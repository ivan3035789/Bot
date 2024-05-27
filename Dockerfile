FROM openjdk as builder
WORKDIR /app
COPY . .

FROM openjdk
WORKDIR /app
COPY --from=builder /app/out/artifacts/Bot_jar/Bot.jar .
COPY src/main/resources/application.properties src/main/resources/application.properties
CMD ["java", "-jar", "Bot.jar"]