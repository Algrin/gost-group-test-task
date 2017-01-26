#!/bin/bash

MVN=`pwd`/automation/maven/bin/mvn	
chmod +x $MVN
$MVN clean -Dspring.profiles.active-dev spring-boot:run
