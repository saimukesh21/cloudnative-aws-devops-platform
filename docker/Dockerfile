
# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Copy Maven wrapper and project files
COPY application/mvnw .
COPY application/.mvn ./.mvn
COPY application/pom.xml .
COPY application/src ./src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build application and run tests
RUN ./mvnw clean package

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy AS runtime

WORKDIR /app

# Install curl for HTTP health checks
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl && \
    rm -rf /var/lib/apt/lists/*

# Create a non-root user
RUN useradd --system --create-home --uid 10001 appuser

# Copy executable JAR from build stage
COPY --from=builder /app/target/*.jar app.jar

# Run as non-root user
USER 10001

EXPOSE 8090

# Check Spring Boot Actuator health endpoint
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
    CMD curl --fail --silent http://localhost:8090/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
