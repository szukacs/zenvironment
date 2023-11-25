FROM openjdk:17
WORKDIR .
CMD ["./gradlew", "clean", "build", "bootJar"]
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]