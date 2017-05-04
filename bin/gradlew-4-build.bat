@echo off
title %~nx0


cd /d %~dp0..
set PROJECT_DIR=%cd%
set LOG_FILE=%~dpn0.log
set GRADLEW_BAT=%PROJECT_DIR%\gradlew.bat
set GRADLEW_OPTS=--info -s


:__selectEnv
echo ************************************************
echo ��ѡ��Ŀ�껷���������Ӧ���֣���
echo 1��dev
echo 2��verify
echo 3��test
echo 4��mirror
echo 5��product
echo.
set /p ENV=��ǰΪ��1��dev����

if "%ENV%" == "" (
    set ENV=dev
) else if "%ENV%" == "1" (
    set ENV=dev
) else if "%ENV%" == "2" (
    set ENV=verify
) else if "%ENV%" == "3" (
    set ENV=test
) else if "%ENV%" == "4" (
    set ENV=mirror
) else if "%ENV%" == "5" (
    set ENV=product
) else (
    echo.
    echo.
    echo Ŀ�껷��%ENV%��֧�֣�������ѡ����Ч��Ŀ�껷����
    goto __selectEnv
)

echo copy /Y %~dp0mtee.exe %JAVA_HOME%\bin\mtee.exe
copy /Y %~dp0mtee.exe %JAVA_HOME%\bin\mtee.exe>nul
echo call %GRADLEW_BAT% %GRADLEW_OPTS% -Penv=%ENV% clean build 2^>^&1 ^|mtee /d/t %LOG_FILE%
call %GRADLEW_BAT% %GRADLEW_OPTS% -Penv=%ENV% clean build 2>&1 |mtee /d/t %LOG_FILE%


pause
