# Use a lightweight base image with Java
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/brendan-sia-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app listens on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=deploy", "-jar", "/app/app.jar"]
