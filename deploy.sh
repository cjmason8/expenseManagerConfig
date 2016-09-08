#!/bin/bash

set -e

while getopts ":p:" opt; do
  case $opt in
    # Provide commands to run
    p)
      PASSWORD="${OPTARG}"
    ;;
    \?)
      echo "Invalid option -$OPTARG" >&2
    ;;
  esac
done

docker login --username=cjmason8 --password=$PASSWORD
docker pull cjmason8/expense-manager:latest
docker run -p 8081:8080 -d cjmason8/expense-manager:latest
