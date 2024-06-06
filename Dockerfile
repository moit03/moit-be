FROM openjdk:17-alpine

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

RUN mkdir -p log/error

ENTRYPOINT ["sh", "-c", "echo $APPLICATION_DEV > /src/main/resources/application-dev.properties && java -jar /app.jar"]