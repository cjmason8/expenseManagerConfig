#!/bin/bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "${SCRIPT_DIR}/expenseManager"

if [ ! -f pom.xml ]; then
  echo "pom.xml not found in $(pwd)" >&2
  exit 1
fi

if [ "${1:-}" = "format-check" ]; then
  shift
  if ! grep -q 'spotless-maven-plugin' pom.xml; then
    echo "spotless-maven-plugin not configured in pom.xml — push expenseManager master with Spotless config" >&2
    exit 1
  fi
  SPOTLESS_VERSION="$(grep -m1 '<spotless.version>' pom.xml | sed -E 's/.*<spotless.version>([^<]+)<.*/\1/')"
  if [ -z "${SPOTLESS_VERSION}" ]; then
    SPOTLESS_VERSION="3.7.0"
  fi
  set -- "com.diffplug.spotless:spotless-maven-plugin:${SPOTLESS_VERSION}:check" "$@"
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
