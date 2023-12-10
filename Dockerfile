FROM openjdk:22-oraclelinux8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} codeanything.jar
ENTRYPOINT ["java","-jar","/codeanything.jar"]