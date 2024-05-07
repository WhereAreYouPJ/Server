FROM openjdk:17
ARG JAR_FILE=core/build/libs/*.jar
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
COPY ${JAR_FILE} core.jar

ENTRYPOINT ["java","-jar","/core.jar"]