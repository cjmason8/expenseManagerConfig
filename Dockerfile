FROM cjmason8/ubuntu-java8:latest

RUN apt-get update
RUN apt-get install -y curl

# Setup Maven
RUN mkdir /maven
WORKDIR /maven

ENV MAVEN_MAJOR 3
ENV MAVEN_VERSION 3.3.9
ENV MAVEN_TGZ_URL http://apache.uberglobalmirror.com/maven/maven-$MAVEN_MAJOR/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz

RUN set -x \
    && curl -fSL "$MAVEN_TGZ_URL" -o maven.tar.gz \
    && tar -xvf maven.tar.gz --strip-components=1 \
    && rm maven.tar.gz*
    
RUN mkdir /app
RUN mkdir /app/expenseManager
COPY src /app/expenseManager/src
COPY mvnw /app/expenseManager
COPY .mvn /app/expenseManager/.mvn
COPY pom.xml /app/expenseManager
RUN chmod +x /app/expenseManager/mvnw
RUN cd /app/expenseManager && ./mvnw install

# Start app
EXPOSE 8081
#CMD ["cd /app/expenseManager && ./mvnw spring-boot:run"]

RUN ls -al /app/expenseManager/target
#COPY ./target/expenseManager-0.0.1-SNAPSHOT.jar ./myapp.jar
RUN sh -c 'touch /app/expenseManager/target/expenseManager-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/expenseManager/target/expenseManager-0.0.1-SNAPSHOT.jar"]
