#!/bin/sh

java -Dspring.devtools.restart.enabled=false \
-noverify $JAVA_OPTS -jar spring-boot-webflux-kotlin-coroutine.jar
