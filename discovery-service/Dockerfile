FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY target/discovery-service-0.0.1-SNAPSHOT.jar /app/discovery-service-0.0.1-SNAPSHOT.jar

EXPOSE 8761

CMD ["java", "-jar", "discovery-service-0.0.1-SNAPSHOT.jar"]