FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl
COPY target/userservice-0.0.1-SNAPSHOT.jar /opt/app/
EXPOSE 8080
CMD ["java", "-showversion", "-jar", "/opt/app/userservice-0.0.1-SNAPSHOT.jar"]