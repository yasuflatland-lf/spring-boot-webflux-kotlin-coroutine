package com.sennproject.springbootwebfluxkotlincoroutine

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@Testcontainers
abstract class AbstractContainerBaseTest {
    companion object {
        val postgres: PostgreSQLContainer =
            PostgreSQLContainer(DockerImageName.parse("postgres:18")).apply {
                withUsername("test")
                withPassword("password")
                withDatabaseName("test")
                withExposedPorts(5432)
                start()
            }
    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.r2dbc.url=${postgres.jdbcUrl.replace("jdbc", "r2dbc")}",
                "spring.r2dbc.username=${postgres.username}",
                "spring.r2dbc.password=${postgres.password}"
            ).applyTo(applicationContext.environment)
        }
    }
}
