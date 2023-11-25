FROM openjdk:17
WORKDIR .
CMD ["./gradlew", "clean", "build", "bootJar"]
ARG JAR_FILE=./build/lib/zenvironment-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]