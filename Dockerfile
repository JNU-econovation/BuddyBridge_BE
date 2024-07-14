FROM openjdk:21-slim

WORKDIR /app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/*.jar app.jar

CMD ["java","-jar","/app/app.jar"]