FROM cjmason8/ubuntu-java8:latest

RUN mkdir /app
COPY target/expensemanager-0.0.1-SNAPSHOT.jar /app/expenseManager.jar

RUN sh -c 'touch /app/expenseManager.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/expenseManager.jar"]
