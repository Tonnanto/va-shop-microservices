FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/order.jar order.jar
ENTRYPOINT ["java", "-jar", "/order.jar", "order"]