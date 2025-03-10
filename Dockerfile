FROM openjdk:17-jdk-slim

WORKDIR /app

# COPY만 Docker-compose 파일의 위치를 기반으로 작동함
COPY . .

# RUN은 현재 파일의 위치를 기반으로 작동함
RUN chmod +x ./gradlew
RUN ./gradlew clean build

ENV JAR_PATH=/app/build/libs
RUN mv ${JAR_PATH}/*.jar /app/app.jar

ENTRYPOINT [ "java", "-Dspring.profiles.active=prod" , "-jar", "app.jar" ]