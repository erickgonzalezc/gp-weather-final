FROM openjdk:17-jdk-alpine
COPY target/prueba-tecnica-0.0.1-SNAPShot.jar java-app.jar
ENTRYPOINT ["java", "-jar", "java-app.jar"]