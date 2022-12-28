FROM eclipse-temurin:17-jdk

WORKDIR home/app
COPY . /home/app
RUN apt-get update && apt-get install -y maven
RUN mvn package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/microservice.jar"]
