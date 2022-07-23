ENV_FIELDS=$(shell cat .env | grep -v '^\#' | awk '/^(.*)=(.*)/ { print $$0 }' | tr '\n' ' ')
PREVIEW_LOCAL_IMAGE=$(shell docker image ls | grep 'local\.test' | awk '{ print $$3 }')
ERR = $(error found an error!)
ORG_PATH := ${CURDIR}

RED=`tput setaf 1`
GREEN=`tput setaf 2`
WHITE=`tput setaf 7`

MAKEFILE_DIR:=$(dir $(abspath $(lastword $(MAKEFILE_LIST))))

.PHONY: help
help: ## Display this help screen
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

.PHONY: clean
clean: ## Cleanup database environment
	rm -fR ./db/mysql_data

.PHONY: down
down: ## Docker compose down
	docker-compose down;

.PHONY: devDB
devDB: ## Run development environment
	docker-compose -f docker-compose-local.yml up;

.PHONY: devBoot
devBoot: ## Run development environment
	export SPRING_PROFILES_ACTIVE=local && ./gradlew clean bootRun ;

.PHONY: run
run: ## Run production environment
	docker-compose up --build;

.PHONY: genapi
genapi: ## Run development environment
	export SPRING_PROFILES_ACTIVE=local && ./gradlew clean generateOpenApiDocs ;

.PHONY: err ## Error function to catch error while running Makefile
    err: ; $(ERR)