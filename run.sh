#!/bin/sh

java -Dspring.devtools.restart.enabled=false $JAVA_OPTS -jar spring-boot-webflux-kotlin-coroutine.jar
