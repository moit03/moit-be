FROM openjdk:17-alpine

COPY env.properties /env.properties
COPY application-dev.properties /application-dev.properties

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
# , "-Dspring.profiles.active=prod"
