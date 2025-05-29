FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copy minimal build files
COPY gradlew .
COPY gradle gradle          # Gradle wrapper directory
COPY build.gradle .
COPY settings.gradle .
COPY src src

# FIX PERMISSIONS (crucial step)
RUN chmod +x gradlew

# Now run Gradle commands
RUN ./gradlew dependencies --no-daemon
RUN ./gradlew bootJar --no-daemon
