# Use a smaller JDK 11 image as base
FROM openjdk:11-jdk-slim

EXPOSE 8080

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY ./target/ms-client-0.0.1-SNAPSHOT.jar /app/ms-client.jar

# Set the active profile using environment variable
ENV SPRING_PROFILES_ACTIVE=develop

# Specify the command to run your Spring Boot application
ENTRYPOINT ["java","-jar","/app/ms-client.jar"]