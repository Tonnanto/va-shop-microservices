FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/customer-exec.jar customer-exec.jar
ENTRYPOINT ["java", "-jar", "/customer-exec.jar", "customer"]