FROM openjdk:21
ARG JAR_FILE=build/libs/StudyConnectMaster*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]