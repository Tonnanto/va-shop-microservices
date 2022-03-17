FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/order-exec.jar order-exec.jar
ENTRYPOINT ["java", "-jar", "/order-exec.jar", "order"]