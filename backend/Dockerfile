FROM openjdk:8-jdk-alpine
WORKDIR .
CMD ["./gradlew", "clean", "build", "bootJar"]
ARG JAR_FILE=demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]