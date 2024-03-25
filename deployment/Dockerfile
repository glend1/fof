# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Copy local code to the container image.
WORKDIR /app
COPY . .

# Build a release artifact.
RUN mvn clean compile assembly:single

# Use OpenJDK to run the .jar
FROM eclipse-temurin:21.0.2_13-jre-alpine

# Copy the jar to the production image from the builder stage.
COPY --from=build /app/target/fof-0.1-jar-with-dependencies.jar /fof-0.1-jar-with-dependencies.jar

# Run the web service on container startup.
CMD ["java", "-jar", "/fof-0.1-jar-with-dependencies.jar"]