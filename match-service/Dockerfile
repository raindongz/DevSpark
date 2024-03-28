FROM bellsoft/liberica-runtime-container:jdk-21-slim-musl
MAINTAINER @RundongZhong
COPY target/matchservice-0.0.1-SNAPSHOT.jar /opt/app/
EXPOSE 8083
CMD ["java", "-showversion", "-jar", "/opt/app/matchservice-0.0.1-SNAPSHOT.jar"]