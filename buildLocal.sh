#!/bin/bash

FULL_IMAGE_NAME=cjmason8/expense-manager
TAG_NAME=$(<LOCAL)

echo "Beginning cleanup step."
echo "Removing docker images for: ${FULL_IMAGE_NAME}"
set +e
# Below to be implemented when docker has been updated to >1.10
#  docker rmi -f $(docker images --format "{{.Repository}}:{{.Tag}}" ${FULL_IMAGE_NAME}) 2> /dev/null
docker rmi $(docker images | grep "^${FULL_IMAGE_NAME}" | awk "{print $3}") 2> /dev/null

echo "Beginning preparation step."
if [ -z "${TAG_NAME}" ]; then
  echo "No tag name defined, unable to continue."
  exit 1
fi

TAG_NAME="${TAG_NAME%.*}.$((${TAG_NAME##*.}+1))"
echo $TAG_NAME > LOCAL

echo "Creating image: ${FULL_IMAGE_NAME}:${TAG_NAME}"

#echo "maven"
cd ../expenseManager
docker run --rm -v "$PWD":/usr/src/mymaven -u 1000:1000 -v "$HOME/.m2":/var/maven/.m2 -e MAVEN_CONFIG=/var/maven/.m2 -w /usr/src/mymaven maven:3.8-openjdk-17 \
      mvn -Duser.home=/var/maven clean install --no-transfer-progress
cd ../expenseManagerConfig
mkdir -p target
cp ../expenseManager/target/expensemanager-0.0.1-SNAPSHOT.jar target

docker build --no-cache --pull -t ${FULL_IMAGE_NAME}:${TAG_NAME} .
