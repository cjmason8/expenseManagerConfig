FROM cjmason8/ubuntu-java8:latest

RUN apt-get update
RUN apt-get install -y curl

RUN mkdir /app
COPY target/eventmanager-0.0.1-SNAPSHOT.jar /app/eventManager.jar

RUN sh -c 'touch /app/eventManager.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/eventManager.jar"]