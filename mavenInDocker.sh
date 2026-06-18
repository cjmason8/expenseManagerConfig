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

# Jenkins runs inside a container; the Docker daemon mounts from the host filesystem.
# Workspace is /var/jenkins_home/... in Jenkins but /jenkinsHome/... on the Docker host.
SOURCE_DIR="$(pwd)"
MOUNT_DIR="${MAVEN_DOCKER_SRC:-${SOURCE_DIR}}"
if [ -z "${MAVEN_DOCKER_SRC:-}" ] && [[ "${SOURCE_DIR}" == /var/jenkins_home/* ]]; then
  JENKINS_HOST_HOME="${JENKINS_HOST_HOME:-/jenkinsHome}"
  MOUNT_DIR="${JENKINS_HOST_HOME}${SOURCE_DIR#/var/jenkins_home}"
fi

if [ ! -f "${MOUNT_DIR}/pom.xml" ]; then
  echo "pom.xml not found at Docker mount source: ${MOUNT_DIR}" >&2
  echo "Workspace path inside Jenkins: ${SOURCE_DIR}" >&2
  echo "Set MAVEN_DOCKER_SRC to the host path visible to the Docker daemon if different." >&2
  exit 1
fi

echo "Running Maven with mount ${MOUNT_DIR} -> /usr/src/mymaven"
docker run --rm \
  -v "${MOUNT_DIR}":/usr/src/mymaven \
  -u 1000:1000 \
  -v "/home/tomcat/.m2":/var/maven/.m2 \
  -e MAVEN_CONFIG=/var/maven/.m2 \
  -w /usr/src/mymaven \
  maven:3.8-openjdk-17 \
  mvn -Duser.home=/var/maven "$@"
