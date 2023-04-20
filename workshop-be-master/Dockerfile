FROM maven:3.8.6-jdk-11 as builder

COPY src /usr/src/app/src

COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml -U clean install -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 5000

COPY --from=builder /usr/src/app/target/event-BE-1.0.0.jar /event-BE-1.0.0.jar

ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-jar","/event-BE-1.0.0.jar"]
