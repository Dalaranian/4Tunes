# Base image
FROM adoptopenjdk/openjdk11:latest

# Set the working directory in the container
WORKDIR /app

# Copy the Maven executable to the container
COPY mvnw .
COPY .mvn .mvn

# Copy the project source code
COPY pom.xml .
COPY src src

# Build the application with Maven
RUN ./mvnw package -DskipTests

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "target/4Tunes-0.0.1-SNAPSHOT.jar"]
