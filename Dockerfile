FROM eclipse-temurin:24-jdk
LABEL authors="admar"

WORKDIR /app

copy target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]