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
# Use the host path for docker -v; /var/jenkins_home is not visible to the Docker daemon.
# Always use maven:3.8-openjdk-17 — Jenkins ships JDK 21 but this project targets Java 17 (Lombok).
if [[ "${SOURCE_DIR}" == /var/jenkins_home/* ]]; then
  JENKINS_HOST_HOME="${JENKINS_HOST_HOME:-/jenkinsHome}"
  MOUNT_DIR="${MAVEN_DOCKER_SRC:-${JENKINS_HOST_HOME}${SOURCE_DIR#/var/jenkins_home}}"
  MAVEN_USER_ID=1000
  MAVEN_GROUP_ID=1000
  M2_DIR="/home/tomcat/.m2"
else
  MOUNT_DIR="${MAVEN_DOCKER_SRC:-${SOURCE_DIR}}"
  MAVEN_USER_ID="$(id -u)"
  MAVEN_GROUP_ID="$(id -g)"
  M2_DIR="${HOME}/.m2"
fi

echo "Running Maven in Docker (JDK 17) with mount ${MOUNT_DIR} -> /usr/src/mymaven"
docker run --rm \
  -v "${MOUNT_DIR}":/usr/src/mymaven \
  -u "${MAVEN_USER_ID}:${MAVEN_GROUP_ID}" \
  -v "${M2_DIR}":/var/maven/.m2 \
  -e MAVEN_CONFIG=/var/maven/.m2 \
  -w /usr/src/mymaven \
  maven:3.8-openjdk-17 \
  mvn -Duser.home=/var/maven "$@"
