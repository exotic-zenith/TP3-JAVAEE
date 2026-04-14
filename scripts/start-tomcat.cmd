@echo off
set "TOMCAT_HOME=C:\Users\Mega-PC\Downloads\apache-tomcat-10.1.53\apache-tomcat-10.1.53"
if not exist "%TOMCAT_HOME%\bin\startup.bat" (
  echo Tomcat folder not found: %TOMCAT_HOME%
  exit /b 1
)
set "CATALINA_HOME=%TOMCAT_HOME%"
set "CATALINA_BASE=%TOMCAT_HOME%"
call "%CATALINA_HOME%\bin\startup.bat"

