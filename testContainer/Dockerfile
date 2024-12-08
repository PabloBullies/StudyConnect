FROM openjdk:21

ARG JAR_FILE=build/libs/itestJar*.jar
COPY testContainer/junit-platform-console-standalone*.jar junit.jar
COPY ${JAR_FILE} test.jar
ENTRYPOINT ["java"]