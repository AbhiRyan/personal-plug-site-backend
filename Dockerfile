# Use the official Eclipse Temurin image for a lean production stage of our multi-stage build.
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the image to /app
WORKDIR /app

# Copy the jar file from your build output
COPY ./apicore/target/*.jar ./app.jar

# Copy the keystore.p12 file
COPY ./keystore.p12 ./keystore.p12

# Run the web service on container startup.
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]