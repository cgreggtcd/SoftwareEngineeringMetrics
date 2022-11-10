FROM openjdk:17
WORKDIR /app
COPY . .
RUN ./mvnw clean install

CMD ./mvnw spring-boot:run