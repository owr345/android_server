FROM eclipse-temurin:17-jre-alpine

# Copy application
COPY build/libs/*.jar app.jar

# Create directory for Render secrets (optional but recommended)
RUN mkdir -p /etc/secrets

# Set environment variable pointing to secret location
ENV FIREBASE_SECRET_PATH /etc/secrets/firebase_key.json

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
