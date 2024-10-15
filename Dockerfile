#
# Build stage
#
FROM maven:3.8.4-jdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:21-jdk
COPY --from=build /target/Meeter-0.0.1-SNAPSHOT.jar meeter.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "meeter.jar"]

# Build
# docker build -t meeter .

# Run
# docker run -p 8080:8080 meeter

# https://github.com/TakiRahal/spring-boot-render