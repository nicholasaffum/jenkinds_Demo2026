FROM eclipse-temurin:17

WORKDIR /app

COPY target/sample-java-app-1.0.jar app.jar

EXPOSE 8000

CMD ["java", "-jar", "app.jar"]
