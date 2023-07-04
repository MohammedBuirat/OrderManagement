# Use a base image with Java 17
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the build files and dependencies
COPY build/libs/*.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Define the command to run when the container starts
CMD ["java", "-jar", "app.jar"]
