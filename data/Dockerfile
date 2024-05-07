FROM openjdk:17
ARG JAR_FILE=data/build/libs/*.jar
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
COPY ${JAR_FILE} data.jar

ENTRYPOINT ["java","-jar","/data.jar"]