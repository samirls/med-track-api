FROM arm64v8/openjdk:17-ea-16-jdk

EXPOSE 8080
WORKDIR /app

ENV DATABASE_CONNECTION_URL=""
ENV SCOPE="prod"
ENV PASSWORD=""
ENV USER=""

COPY target/minha-clinica.jar /app/minha-clinica.jar

ENTRYPOINT ["java", "-jar", "minha-clinica.jar"]
