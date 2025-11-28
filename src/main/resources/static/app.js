// Frontend: load parts, allow click/drag to add, and explicit Calculate button
let allParts = [];
const selected = {};

async function loadParts() {
  const res = await fetch('/api/parts');
  const parts = await res.json();
  allParts = parts;
  const container = document.getElementById('parts');
  container.innerHTML = '';
  const categories = {};
  parts.forEach(p => {
    if (!categories[p.category]) categories[p.category] = [];
    categories[p.category].push(p);
  });

  for (const cat of Object.keys(categories)) {
    const section = document.createElement('div');
    section.className = 'category';
    const h = document.createElement('h3');
    h.textContent = cat;
    section.appendChild(h);

    categories[cat].forEach(p => {
      const el = document.createElement('div');
      el.className = 'part';
      el.dataset.id = p.id;
      el.innerHTML = `<div class="meta"><div class="name">${p.name}</div><div class="small">${p.category}</div></div><div class="price">₹ ${p.priceInINR}</div>`;
      // Add button for selection (preferred over drag)
      const addBtn = document.createElement('button');
      addBtn.className = 'btn add';
      addBtn.textContent = 'Add';
      addBtn.addEventListener('click', (ev) => {
        ev.stopPropagation();
        addToBuild(p.id);
      });
      el.appendChild(addBtn);
      section.appendChild(el);
    });
    container.appendChild(section);
  }
}

function setupDrop() {
  const build = document.getElementById('build');
  build.addEventListener('dragover', ev => ev.preventDefault());
  build.addEventListener('drop', ev => {
    ev.preventDefault();
    const id = ev.dataTransfer.getData('text/plain');
    addToBuild(id);
  });
}

function addToBuild(id) {
  const p = allParts.find(x => x.id === id);
  if (!p) return;
  if (!selected[id]) selected[id] = { ...p, qty: 0 };
  selected[id].qty += 1;
  renderBuild();
}

function renderBuild() {
  const buildList = document.getElementById('buildList');
  buildList.innerHTML = '';
  for (const k of Object.keys(selected)) {
    const it = selected[k];
    const div = document.createElement('div');
    div.className = 'build-item';
    div.innerHTML = `<div>${it.name} <div class="small">₹ ${it.priceInINR} each</div></div><div><span class="small">x ${it.qty}</span> <button data-id="${k}" class="btn">-</button></div>`;
    div.querySelector('button').addEventListener('click', () => {
      it.qty -= 1;
      if (it.qty <= 0) delete selected[k];
      renderBuild();
    });
    buildList.appendChild(div);
  }
}

// Calculate total via server API (explicit button), fallback to client-side sum if API fails
async function calculateTotal() {
  const items = Object.values(selected).map(s => ({ id: s.id, qty: s.qty }));
  if (items.length === 0) {
    document.getElementById('total').textContent = 'Total: ₹ 0';
    return;
  }
  try {
    const res = await fetch('/api/price', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ items })
    });
    const data = await res.json();
    document.getElementById('total').textContent = `Total: ₹ ${data.totalInINR}`;
  } catch (e) {
    // fallback: compute on client
    const total = Object.values(selected).reduce((acc, s) => acc + (s.priceInINR * s.qty), 0);
    document.getElementById('total').textContent = `Total: ₹ ${total} (calculated locally)`;
  }
}

function clearBuild() {
  for (const k of Object.keys(selected)) delete selected[k];
  renderBuild();
  document.getElementById('total').textContent = 'Total: ₹ 0';
}

window.addEventListener('DOMContentLoaded', () => {
  loadParts();
  setupDrop();
  document.getElementById('calcBtn').addEventListener('click', calculateTotal);
  document.getElementById('clearBtn').addEventListener('click', clearBuild);
});
