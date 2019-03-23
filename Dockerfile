FROM cjmason8/alpine-openjdk:jre-11.0.2.9-alpine-v2

COPY target/expensemanager-0.0.1-SNAPSHOT.jar /app/expenseManager.jar
COPY run.sh /app/run.sh

USER root

RUN chown localUser /app/expenseManager.jar
RUN chown localUser /app/run.sh

USER localUser

CMD ["/app/run.sh"]