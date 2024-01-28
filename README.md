# spring-boot-webflux-kotlin-coroutine
![GitHub Workflow Status (with branch)](https://img.shields.io/github/actions/workflow/status/yasuflatland-lf/spring-boot-webflux-kotlin-coroutine/spring-boot-webflux-kotlin-coroutine.yml?branch=develop)
[![codecov](https://codecov.io/github/yasuflatland-lf/spring-boot-webflux-kotlin-coroutine/branch/develop/graph/badge.svg?token=Kj9b0BQZcV)](https://codecov.io/github/yasuflatland-lf/spring-boot-webflux-kotlin-coroutine)

A sample of Spring boot WebFlux and Kotlin Coroutine with Handler and Router. In this sample, implementing Todo model, handler and router with database (MySQL) testing.

# Reqirements
- Java 17
- Docker 4.26.1 >=
- gradle 8.1.1 >=

# Getting Started
Please make sure Docker is up and running.

## Production
```bash
$ make run
```
`application.yml` will be referred for the configuration. This command does
- Build Docker image with the implementation
- Spin up MySQL and Springboot application (this application) by `docker-compose`

## Development
```bash
$ make devDB
$ make devBoot
```
`application-local.yml` will be referred for the configuration.

`make devDB` only spins up MySQL, and `make devBoot` run this application by development mode via `gradle`.

### How to run test
```bash
$ gradle test --info
```
## Clean up environment
```bash
$ make clean
```

This command cleans up database.

# OpenAPI
## How to access Swagger UI
```bash
http://localhost:8080/webjars/swagger-ui/index.html
```

# Tips

## Makefile commands
You can run all commands by using Makefile. All available commands is displayed by typing,
```bash
$ make help
```
