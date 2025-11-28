FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy only the files required for dependency resolution first for better caching
COPY pom.xml mvnw* ./
COPY .mvn .mvn
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests package || true

# Copy full project and build
COPY src ./src
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
FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
