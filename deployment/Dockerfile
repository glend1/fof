# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8.1-openjdk-11-slim AS build

# Copy local code to the container image.
WORKDIR /app
COPY . .

# Build a release artifact.
RUN mvn clean compile assembly:single

# Use OpenJDK to run the .jar
FROM openjdk:11-jdk-alpine

# Copy the jar to the production image from the builder stage.
COPY --from=build /app/target/fof-0.1-jar-with-dependencies.jar /fof-0.1-jar-with-dependencies.jar

# Run the web service on container startup.
CMD ["java", "-jar", "/fof-0.1-jar-with-dependencies.jar"]