FROM eclipse-temurin:17-jdk

WORKDIR home/app
COPY . /home/app
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/app/microservice.jar"]
