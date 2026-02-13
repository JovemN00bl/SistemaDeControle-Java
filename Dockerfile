FROM eclipse-temurin:17-jdk-alpine
LABEL authors="admar"

WORKDIR /app

copy target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]