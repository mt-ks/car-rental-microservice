FROM openjdk:22

# Set the working directory inside the container
WORKDIR /app

# Copy Maven/Gradle wrapper and config files first
COPY . .

# Add Maven Wrapper execute permissions
RUN chmod +x ./mvnw


# Expose the application port
EXPOSE 3001

# Command to run the application with hot reload enabled
CMD ["./mvnw", "spring-boot:run"]
