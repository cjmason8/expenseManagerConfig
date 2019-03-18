#!/bin/bash

RANCHER_ACCESS_KEY=$1
RANCHER_SECRET_KEY=$2
RANCHER_URL=http://80.241.221.122:8080/v2-beta/projects/1a5
ENV_NAME=prd
COMPOSE_PROJECT_NAME=expense-manager
COMPOSE_FILE=${BASE_DIR}/${ENV_NAME}/docker-compose-${ENV_NAME}.yml
BASE_DIR=${PWD}
TAG_NAME=$(<VERSION)

echo "VER=$TAG_NAME"

echo "TAG_NAME=$TAG_NAME" > .env

echo "Force pulling..."
rancher-compose --url ${RANCHER_URL} --access-key ${RANCHER_ACCESS_KEY} --secret-key ${RANCHER_SECRET_KEY} pull

echo "Starting deployment..."
#rancher-compose --url ${RANCHER_URL} --access-key ${RANCHER_ACCESS_KEY} --secret-key ${RANCHER_SECRET_KEY} --rancher-file ${BASE_DIR}/rancher-compose.yml --project-name expenseManager --file ${BASE_DIR}/${ENV_NAME}/docker-compose-${ENV_NAME}.yml up --upgrade -d --pull --batch-size 1

if [ $? -eq 0 ]; then
  echo "Deploy success! Confirming..."
  #rancher-compose --url ${RANCHER_URL} --access-key ${RANCHER_ACCESS_KEY} --secret-key ${RANCHER_SECRET_KEY} --project-name expenseManager --file ${BASE_DIR}/${ENV_NAME}/docker-compose-${ENV_NAME}.yml up --confirm-upgrade -d --batch-size 1
else
  echo "Deploy failed :( rolling back..."
  #rancher-compose --url ${RANCHER_URL} --access-key ${RANCHER_ACCESS_KEY} --secret-key ${RANCHER_SECRET_KEY} --project-name expenseManager --file ${BASE_DIR}/${ENV_NAME}/docker-compose-${ENV_NAME}.yml up --rollback -d --batch-size 1
fi
