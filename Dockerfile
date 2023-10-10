#
# Build stage
#
FROM maven:3.8.2-jdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/Meeter-0.0.1-SNAPSHOT.jar meeter.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "meeter.jar"]
