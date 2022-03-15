FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/article-component.jar article-component.jar
ENTRYPOINT ["java", "-jar", "/article-component.jar", "article-component"]