# Container in which to build the application
FROM gradle:8.1.1-jdk11-alpine as builder

# Copy the source code into the builder container
WORKDIR /app
COPY . .

# Build the application in the builder container
RUN gradle assemble

# Container in which to run the application
FROM eclipse-temurin:17.0.10_7-jdk-alpine

# Copy the jar from the builder container into the run container
COPY --from=builder /app/build/libs/spring-boot-webflux-kotlin-coroutine-*.jar spring-boot-webflux-kotlin-coroutine.jar

# Copy run shell
COPY run.sh .
RUN chmod +x ./run.sh

# Run the application
EXPOSE 8080

CMD ["./run.sh"]