FROM openjdk:17
ARG JAR_FILE=application/build/libs/*.jar
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]