FROM openjdk:17-alpine

COPY env.properties /env.properties

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]

FROM openjdk:11-jre-slim
COPY target/your-application.jar /app/your-application.jar
CMD ["java", "-jar", "/app/your-application.jar"]