#!/bin/bash

set -e

cd "$(dirname "$0")"

while getopts ":p:e:" opt; do
  case $opt in
    # Provide commands to run
    p)
      PASSWORD="${OPTARG}"
    ;;
    e)
      ENV="${OPTARG}"
    ;;
    \?)
      echo "Invalid option -$OPTARG" >&2
    ;;
  esac
done

#echo "docker stop"
#docker stop ${ENV}_eventManager_1
echo "docker login"
docker login --username=cjmason8 --password=$PASSWORD
echo "docker pull"
docker pull cjmason8/event-manager:latest
echo "docker compose"
pwd
docker-compose -f ${ENV}/docker-compose-${ENV}.yml up -d eventManager
echo "finished"
