package com.example.pcbuilder.controller;

import com.example.pcbuilder.model.Part;
import com.example.pcbuilder.service.PartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final PartService partService;

    public ApiController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/parts")
    public List<Part> parts() {
        return partService.getAll();
    }

    @PostMapping("/price")
    public ResponseEntity<?> calcPrice(@RequestBody Map<String, Object> body) {
        // expect { "items": [ { "id": "cpu1", "qty": 1 }, ... ] }
        var items = (List<Map<String, Object>>) body.get("items");
        long total = 0;
        for (Map<String, Object> it : items) {
            String id = (String) it.get("id");
            int qty = ((Number) it.getOrDefault("qty", 1)).intValue();
            var p = partService.findById(id);
            if (p.isPresent()) {
                total += p.get().getPriceInINR() * qty;
            }
        }
        return ResponseEntity.ok(Map.of("totalInINR", total));
    }
}
