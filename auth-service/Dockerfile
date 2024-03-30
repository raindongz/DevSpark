# Build Stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests


# Execution Stage
FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl
MAINTAINER @RundongZhong
COPY --from=build /home/app/target/authservice-0.0.1-SNAPSHOT.jar /usr/local/lib/authservice-0.0.1-SNAPSHOT.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","/usr/local/lib/authservice-0.0.1-SNAPSHOT.jar"]

#FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl
#COPY target/config-server-0.0.1-SNAPSHOT.jar /opt/app/
#EXPOSE 8888
#CMD ["java", "-showversion", "-jar", "/opt/app/config-server-0.0.1-SNAPSHOT.jar"]