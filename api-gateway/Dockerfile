FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar", "api-gateway"]