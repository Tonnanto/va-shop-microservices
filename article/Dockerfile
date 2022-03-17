FROM openjdk:16
LABEL maintainer="anton@stamme.de"
ADD target/article-exec.jar article-exec.jar
ENTRYPOINT ["java", "-jar", "/article-exec.jar", "article"]