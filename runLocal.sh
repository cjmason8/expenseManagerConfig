#!/bin/sh

java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -Djava.security.egd=file:/dev/./urandom -jar /app/expenseManager.jar
