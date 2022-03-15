FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/customer.jar customer.jar
ENTRYPOINT ["java", "-jar", "/customer.jar", "customer"]