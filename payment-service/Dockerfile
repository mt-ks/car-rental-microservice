FROM openjdk:22

WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy the source code
COPY src ./src

RUN chmod +x ./mvnw

EXPOSE 3003

CMD ["./mvnw", "spring-boot:run"]
