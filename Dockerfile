FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy the POM first for dependency resolution (we don't use the wrapper files in this repo)
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests package || true

# Copy full project and build
COPY . ./
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /workspace/target/pc-builder-0.0.1-SNAPSHOT.jar /app/app.jar

# Allow overriding JVM options and respect Render's $PORT env var
ENV JAVA_OPTS=""
ENV PORT=8080
EXPOSE 8080

CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT} -jar /app/app.jar"]

