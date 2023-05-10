FROM openjdk:17-alpine

EXPOSE 8091
WORKDIR /app
COPY target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
