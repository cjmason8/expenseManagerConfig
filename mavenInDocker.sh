#!/bin/bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${SCRIPT_DIR}/expenseManager"

if [ ! -f pom.xml ]; then
  echo "pom.xml not found in $(pwd)" >&2
  exit 1
fi

echo "Running Maven in $(pwd)"
docker run --rm \
  -v "$(pwd)":/usr/src/mymaven \
  -u 1000:1000 \
  -v "/home/tomcat/.m2":/var/maven/.m2 \
  -e MAVEN_CONFIG=/var/maven/.m2 \
  -w /usr/src/mymaven \
  maven:3.8-openjdk-17 \
  mvn -Duser.home=/var/maven "$@"
