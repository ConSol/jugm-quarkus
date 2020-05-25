#!/usr/bin/env bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "${SCRIPT_DIR}"

echo "================================================================================"
echo "Starting docker deployments: jaeger, postgres and keycloak"
echo "================================================================================"
docker-compose up -d tracing-service dbms-service oauth2-service

if [[ ${MIGRATE_DATABASES} == true ]]; then
  echo "================================================================================"
  echo "Migrating database"
  echo "================================================================================"
  cd ..
  ./mvnw flyway:migrate
  cd localdeployment
fi
