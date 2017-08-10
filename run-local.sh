#!/bin/bash

AUTH_SERVICE_END_POINT=http://localhost:8082
DB_DRIVER=org.postgresql.Driver
DB_PASS=postgres
DB_URL=jdbc:postgresql://localhost:5432/expensemanager
DB_USER=postgres
HIBERNATE_DIALECT=au.com.mason.expensemanager.dao.CustomPostgreSqlDialect
HIBERNATE_HBM2DDL_AUTO=update

export DB_DRIVER
export DB_PASS
export DB_URL
export DB_USER
export HIBERNATE_DIALECT
export HIBERNATE_HBM2DDL_AUTO
export AUTH_SERVICE_END_POINT

mvn clean install
mvn -Dserver.port=8083 spring-boot:run

