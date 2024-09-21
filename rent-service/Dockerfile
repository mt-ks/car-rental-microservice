FROM openjdk:22

# Set the working directory inside the container
WORKDIR /app

# Copy Maven/Gradle wrapper and config files first
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy the source code
COPY src ./src

# Add Maven Wrapper execute permissions
RUN chmod +x ./mvnw


# Expose the application port
EXPOSE 3002


# Command to run the application with hot reload enabled
CMD ["./mvnw", "spring-boot:run"]
