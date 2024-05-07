FROM openjdk:17
ARG JAR_FILE=domain/build/libs/*.jar
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
COPY ${JAR_FILE} domain.jar

ENTRYPOINT ["java","-jar","/domain.jar"]