# Use an official Gradle image to build the Spring Boot app
FROM gradle:8.10.2-jdk21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the gradle build files first to leverage Docker layer caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Download dependencies before copying the entire project
RUN gradle build --no-daemon || return 0

# Copy the rest of the application code
COPY . .

RUN gradle build --no-daemon

# Use an official OpenJDK runtime as a base image for running the application
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the first stage
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
# Run the main class from the JAR
ENTRYPOINT ["java", "-cp", "app.jar", "de.hhn.aib3.Main"]