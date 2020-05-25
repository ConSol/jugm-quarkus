@ECHO off

SETLOCAL
SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

ECHO ================================================================================
ECHO Shutting down docker deployments
ECHO ================================================================================
docker-compose down

CD /D %FROM_DRIVE%
CD %FROM_PATH%
IF %errorlevel% NEQ 0 EXIT /b %errorlevel%
ENDLOCAL