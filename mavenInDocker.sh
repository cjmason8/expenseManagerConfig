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

SOURCE_DIR="$(pwd)"

# Jenkins runs in Docker with /jenkinsHome:/var/jenkins_home (see ../jenkins/docker-compose.yml).
# Maven is installed in the Jenkins image — run it natively to avoid bind-mounting the workspace
# through the host Docker socket (/var/jenkins_home is not a valid host path for -v).
if [[ "${SOURCE_DIR}" == /var/jenkins_home/* ]] && command -v mvn >/dev/null 2>&1; then
  echo "Running Maven natively in Jenkins at ${SOURCE_DIR}"
  mvn -Duser.home="${MAVEN_USER_HOME:-/var/jenkins_home/.m2}" "$@"
  exit $?
fi

# Local / non-Jenkins: run Maven in a container with the current directory mounted.
echo "Running Maven in Docker at ${SOURCE_DIR}"
docker run --rm \
  -v "${SOURCE_DIR}":/usr/src/mymaven \
  -u "$(id -u):$(id -g)" \
  -v "${HOME}/.m2":/var/maven/.m2 \
  -e MAVEN_CONFIG=/var/maven/.m2 \
  -w /usr/src/mymaven \
  maven:3.8-openjdk-17 \
  mvn -Duser.home=/var/maven "$@"
