FROM openjdk:8u171-jdk-alpine3.8

RUN mkdir /app
COPY target/expensemanager-0.0.1-SNAPSHOT.jar /app/expenseManager.jar

RUN sh -c 'touch /app/expenseManager.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/expenseManager.jar"]
