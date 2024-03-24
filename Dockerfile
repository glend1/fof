# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8.1-openjdk-16 as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://docs.docker.com/samples/java/
FROM adoptopenjdk:16-jre-hotspot

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/fof-0.1.jar /fof-0.1.jar

# Run the web service on container startup.
CMD ["java","-jar","/fof-0.1.jar"]