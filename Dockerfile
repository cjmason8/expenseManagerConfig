FROM cjmason8/alpine-openjdk:8u171-jdk-alpine3.8

COPY target/expensemanager-0.0.1-SNAPSHOT.jar /app/expenseManager.jar
COPY run.sh /app/run.sh

RUN sh -c 'touch /app/expenseManager.jar'
CMD ["/app/run.sh"]