package com.sennproject.springbootwebfluxkotlincoroutine

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
abstract class AbstractContainerBaseTest {
    companion object {
        val mysql: MySQLContainer<*> = MySQLContainer<Nothing>(DockerImageName.parse("mysql:8.0"))
            .apply {
                withUsername("root")
                withPassword("password")
                withDatabaseName("test")
                withConfigurationOverride("db/mysql_conf_override")
//                withFileSystemBind("./build/log", "/var/log/mysql")
                withUrlParam("useSsl", "false")
                start()
            }
    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.r2dbc.url=${
                    mysql.jdbcUrl.replace(
                        "jdbc",
                        "r2dbc"
                    )
                }&characterEncoding=utf-8&useSSL=false&&sslMode=DISABLED",
                "spring.r2dbc.username=${mysql.username}",
                "spring.r2dbc.password=${mysql.password}",
                // Due to flyway is not integrated with r2dbc.
                "spring.flyway.url=${mysql.jdbcUrl}",
                "spring.flyway.user=${mysql.username}",
                "spring.flyway.password=${mysql.password}",
                "spring.flyway.locations=classpath:db/migration",
            ).applyTo(applicationContext.environment)
        }
    }


}