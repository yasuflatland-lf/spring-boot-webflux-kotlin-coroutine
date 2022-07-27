# spring-boot-webflux-kotlin-coroutine
A sample of Spring boot WebFlux and Kotlin Coroutine with Handler and Router. In this sample, implementing Todo model, handler and router with database (MySQL) testing.

# Reqirements
- Java 11
- Docker 4.6.1 >=

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


## Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.6/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.6/gradle-plugin/reference/html/#build-image)
* [Testcontainers Elasticsearch Container Reference Guide](https://www.testcontainers.org/modules/elasticsearch/)
* [Testcontainers R2DBC support Reference Guide](https://www.testcontainers.org/modules/databases/r2dbc/)
* [Testcontainers MySQL Module Reference Guide](https://www.testcontainers.org/modules/databases/mysql/)
* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring/docs/5.3.17/spring-framework-reference/languages.html#coroutines)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#web.reactive)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#boot-features-r2dbc)
* [Testcontainers](https://www.testcontainers.org/)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup)
* [Spring Data Elasticsearch (Access+Driver)](https://docs.spring.io/spring-boot/docs/2.6.5/reference/htmlsingle/#boot-features-elasticsearch)

## Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Acessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

## Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [R2DBC Homepage](https://r2dbc.io)

