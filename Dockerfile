FROM openjdk:15
MAINTAINER gustavoborges
WORKDIR /opt/app

ARG JAR_FILE=target/tour-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profile.active=dev","-jar", "app.jar"]