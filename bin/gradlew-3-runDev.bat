@echo off
title %~nx0


cd /d %~dp0..
set PROJECT_DIR=%cd%
set LOG_FILE=%~dpn0.log
set GRADLEW_BAT=%PROJECT_DIR%\gradlew.bat
set GRADLEW_OPTS=--info -s


echo copy /Y %~dp0mtee.exe %JAVA_HOME%\bin\mtee.exe
copy /Y %~dp0mtee.exe %JAVA_HOME%\bin\mtee.exe>nul
echo call %GRADLEW_BAT% %GRADLEW_OPTS% -p %PROJECT_DIR%\subprojects\frontend runDev 2^>^&1 ^|mtee /d/t %LOG_FILE%
call %GRADLEW_BAT% %GRADLEW_OPTS% -p %PROJECT_DIR%\subprojects\frontend runDev 2>&1 |mtee /d/t %LOG_FILE%


pause
