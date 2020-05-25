@ECHO off

SETLOCAL EnableDelayedExpansion

SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

ECHO ================================================================================
ECHO Starting docker deployments: jaeger, postgres and keycloak
ECHO ================================================================================
CALL docker-compose up -d tracing-service dbms-service oauth2-service
IF !ERRORLEVEL! NEQ 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b !ERRORLEVEL!
)

IF "!MIGRATE_DATABASES!" == "true" (
  ECHO ================================================================================
  ECHO Migrating database
  ECHO ================================================================================
  CD ..
  CALL mvnw.cmd flyway:migrate
  IF !ERRORLEVEL! NEQ 0 (
    CD /D %FROM_DRIVE%
    CD %FROM_PATH%
    exit /b %ERRORLEVEL%
  )
  CD localdeployment
)