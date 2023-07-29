FROM openjdk:17-jdk-alpine3.13

EXPOSE 8080
WORKDIR /app

ENV DATABASE_CONNECTION_URL=""
ENV SCOPE="prod"
ENV PASSWORD=""
ENV USER=""

COPY target/minha-clinica.jar /app/minha-clinica.jar

ENTRYPOINT ["java", "-jar", "minha-clinica.jar"]
