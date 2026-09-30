FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY application/pom.xml .
COPY application/src ./src

RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy AS runtime

WORKDIR /app

RUN useradd --system --create-home --uid 10001 appuser

COPY --from=builder /app/target/*.jar app.jar

USER 10001

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]
