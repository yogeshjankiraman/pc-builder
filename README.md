# PC Builder (Java + Spring Boot)

This is a minimal PC builder web app written in Java with Spring Boot. It provides a drag-and-drop UI to select PC parts and computes the total price in Indian Rupees (INR).

Features
- Browse parts by category
- Drag parts into the build area
- Quantity adjustments and live total price calculation (INR)

Run locally

Requirements: Java 17+, Maven

Build and run:
```powershell
cd pc-builder
mvn package
java -jar target\pc-builder-0.0.1-SNAPSHOT.jar
```

Open `http://localhost:8080` in your browser.

Docker

Build image:
```powershell
docker build -t pc-builder:latest .
docker run -p 8080:8080 pc-builder:latest
```

Deploy

Push this repo to GitHub and connect to a platform like Render or Railway; they will build the Maven project and provide a live URL.

Next steps you can add
- Persist user builds and authentication
- Add more parts and categories, prices from live APIs
- Save/load builds and generate shareable links
