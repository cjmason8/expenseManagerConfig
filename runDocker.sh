#!/bin/bash

set -e

ENV=lcl
FULL_IMAGE_NAME="expense-manager"

echo "Building version."
TAG_NAME=0.0.1
echo $TAG_NAME > VERSION
echo -e "TAG_NAME=$TAG_NAME" > .env

echo "Creating image: ${FULL_IMAGE_NAME}:${TAG_NAME}"
cd ../expenseManager
mvn clean install

cd ../expenseManagerConfig
mkdir -p target
cp ../expenseManager/target/expensemanager-0.0.1-SNAPSHOT.jar target
docker build -f Dockerfile_lcl --no-cache --pull -t ${FULL_IMAGE_NAME}:${TAG_NAME} .
docker tag ${FULL_IMAGE_NAME}:${TAG_NAME} cjmason8/${FULL_IMAGE_NAME}:${TAG_NAME}
docker tag ${FULL_IMAGE_NAME}:${TAG_NAME} cjmason8/${FULL_IMAGE_NAME}:latest

docker-compose -f ${ENV}/docker-compose-${ENV}.yml up -d expenseManager
