# === Stage 1: Build the application ===
FROM gradle:8.5-jdk21 AS build

# Use a volume mount to reuse Gradle caches (optional but recommended in CI/CD)
ENV GRADLE_USER_HOME=/home/gradle/.gradle

# Set work directory
WORKDIR /app

# Copy Gradle files and pre-download dependencies (leverage Docker cache)
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle build -x test --no-daemon || return 0

# Copy the rest of the project
COPY . .

# Build the app (skip tests for faster build)
RUN gradle bootJar -x test --no-daemon

# === Stage 2: Run the application with a slim runtime ===
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
