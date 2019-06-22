#!/bin/bash

set -e

ENV=lcldoc
FULL_IMAGE_NAME="expense-manager"

git checkout master
git pull origin master

echo "Building version."
TAG_NAME=$(<LOCAL)
TAG_NAME="${TAG_NAME%.*}.$((${TAG_NAME##*.}+1))"
echo $TAG_NAME > LOCAL

echo -e "TAG_NAME=$TAG_NAME" > .env

echo "Creating image: ${FULL_IMAGE_NAME}:${TAG_NAME}"
cd ../expenseManager
docker run --rm -v "$PWD":/usr/src/mymaven -u 1000:1000 -v "$HOME/.m2":/var/maven/.m2 -e MAVEN_CONFIG=/var/maven/.m2 -w /usr/src/mymaven maven:3.6.1-jdk-12 mvn -Duser.home=/var/maven clean install --no-transfer-progress

cd ../expenseManagerConfig
mkdir -p target
cp ../expenseManager/target/expensemanager-0.0.1-SNAPSHOT.jar target
docker build -f Dockerfile_lcl --no-cache --pull -t ${FULL_IMAGE_NAME}:${TAG_NAME} .
docker tag ${FULL_IMAGE_NAME}:${TAG_NAME} cjmason8/${FULL_IMAGE_NAME}:${TAG_NAME}
docker tag ${FULL_IMAGE_NAME}:${TAG_NAME} cjmason8/${FULL_IMAGE_NAME}:latest

docker-compose -f ${ENV}/docker-compose-${ENV}.yml up -d expenseManager
