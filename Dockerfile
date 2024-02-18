#FROM ubuntu:latest AS build
#RUN apt-get update
#RUN apt-get install openjdk-21-jdk -y
#COPY . .
#RUN apt-get install maven -y
#RUN mvn clean install
#
#FROM openjdk:21-jdk-slim
#EXPOSE 8080
#COPY --from=build /target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]


FROM maven:3.9.6-eclipse-temurin-21-jammy as build
COPY . .
RUN mvn clean package -X -DskipTests

FROM openjdk:22-ea-21-jdk-slim-bullseye
WORKDIR app
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]