FROM openjdk:17
WORKDIR /app
EXPOSE 8080
COPY target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]