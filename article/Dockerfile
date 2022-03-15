FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/article.jar article.jar
ENTRYPOINT ["java", "-jar", "/article.jar", "article"]