FROM openjdk:17-alpine

WORKDIR /app

COPY "build/libs/hs-files-compressor-*.jar" application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]
