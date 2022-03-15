FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/service-discovery.jar service-discovery.jar
ENTRYPOINT ["java", "-jar", "/service-discovery.jar", "service-discovery"]