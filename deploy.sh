#!/bin/bash

RANCHER_ACCESS_KEY=$1
RANCHER_SECRET_KEY=$2
RANCHER_URL=$3
ENV_NAME=$4
COMPOSE_PROJECT_NAME=expenseManager
COMPOSE_FILE=${PWD}/${ENV_NAME}/docker-compose-${ENV_NAME}.yml
TAG_NAME=$(<VERSION)

if [ $ENV_NAME = 'lcl' ]; then
  TAG_NAME=$(<LOCAL)
fi

export TAG_NAME
export RANCHER_URL
export RANCHER_ACCESS_KEY
export RANCHER_SECRET_KEY
export COMPOSE_PROJECT_NAME
export COMPOSE_FILE

echo "VER=$TAG_NAME"

echo "Force pulling..."
rancher-compose pull

echo "Starting deployment..."
rancher-compose up --upgrade -d --pull --batch-size 1

if [ $? -eq 0 ]; then
  echo "Deploy success! Confirming..."
  rancher-compose up --confirm-upgrade -d --batch-size 1
else
  echo "Deploy failed :( rolling back..."
  rancher-compose up --rollback -d --batch-size 1
fi
