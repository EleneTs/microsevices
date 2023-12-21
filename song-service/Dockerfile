FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY target/song-service-0.0.1-SNAPSHOT.jar /app/song-service-0.0.1-SNAPSHOT.jar

EXPOSE 8081

CMD ["java", "-jar", "song-service-0.0.1-SNAPSHOT.jar"]