@echo off
 	
set MAVEN_HOME=%~dp0\automation\maven
call %MAVEN_HOME%\bin\mvn clean -Dspring.profiles.active-dev spring-boot:run
rem pause