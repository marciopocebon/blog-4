FROM openjdk:8-jdk-alpine

RUN mkdir /blog

WORKDIR /blog

COPY build/libs/blog-1.0-SNAPSHOT.jar /blog/blog-1.0-SNAPSHOT.jar

CMD ["sh", "-c", "java -jar blog-1.0-SNAPSHOT.jar"]
