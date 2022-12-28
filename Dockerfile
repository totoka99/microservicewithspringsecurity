FROM eclipse-temurin:17-jdk
EXPOSE 8080
WORKDIR home/app
COPY target/microservice-demo.jar microservice.jar
ENTRYPOINT ["java", "-jar", "/home/app/microservice.jar"]
