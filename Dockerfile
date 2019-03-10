FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-alpine-slim

RUN adduser -D -u 1000 localUser

RUN mkdir /app

RUN chown localUser /app

COPY target/expensemanager-0.0.1-SNAPSHOT.jar /app/expenseManager.jar
COPY run.sh /app/run.sh

RUN chown localUser /app/expenseManager.jar
RUN chown localUser /app/run.sh

USER localUser

CMD ["/app/run.sh"]