# Container in which to build the application
FROM gradle:7.4.2-jdk11 as builder

# Copy the source code into the builder container
WORKDIR /app
COPY . .

# Build the application in the builder container
RUN gradle assemble

# Container in which to run the application
FROM eclipse-temurin:11.0.15_10-jdk-alpine

# Copy the jar from the builder container into the run container
COPY --from=builder /app/build/libs/spring-boot-webflux-kotlin-coroutine-*.jar spring-boot-webflux-kotlin-coroutine.jar

# Copy run shell
COPY run.sh .
RUN chmod +x ./run.sh

# Run the application
EXPOSE 8080

CMD ["./run.sh"]