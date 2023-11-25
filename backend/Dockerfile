FROM openjdk:17
WORKDIR .
RUN ./gradlew clean
RUN ./gradlew build
RUN ./gradlew bootJar
ARG JAR_FILE=./build/lib/zenvironment-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]