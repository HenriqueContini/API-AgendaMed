FROM maven:latest AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

FROM eclipse-temurin:21-jdk
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
