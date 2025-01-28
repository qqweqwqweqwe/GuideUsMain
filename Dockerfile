FROM amazoncorretto:17

COPY ./build/libs/GuideUs-0.0.1-SNAPSHOT.jar /app/GuideUs.jar

ENTRYPOINT ["java", "-jar", "/app/GuideUs.jar"]

EXPOSE 8080

