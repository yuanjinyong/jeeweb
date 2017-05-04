@echo off
title %~nx0


cd /d %~dp0..
set PROJECT_DIR=%cd%
set LOG_FILE=%~dpn0.log
set GRADLEW_BAT=%PROJECT_DIR%\gradlew.bat
set GRADLEW_OPTS=--info -s


:__selectEnv
echo ************************************************
echo 请选择目标环境（输入对应数字）：
echo 1、dev
echo 2、verify
echo 3、test
echo 4、mirror
echo 5、product
echo.
set /p ENV=当前为“1、dev”：

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
    echo 目标环境%ENV%不支持，请重新选择有效的目标环境！
    goto __selectEnv
)

echo copy /Y %~dp0mtee.exe %JAVA_HOME%\bin\mtee.exe
copy /Y %~dp0mtee.exe %JAVA_HOME%\bin\mtee.exe>nul
echo call %GRADLEW_BAT% %GRADLEW_OPTS% -Penv=%ENV% clean build 2^>^&1 ^|mtee /d/t %LOG_FILE%
call %GRADLEW_BAT% %GRADLEW_OPTS% -Penv=%ENV% clean build 2>&1 |mtee /d/t %LOG_FILE%


pause
