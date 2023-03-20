package com.sennproject.springbootwebfluxkotlincoroutine

import io.r2dbc.spi.ConnectionFactory
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

/**
 * @author Yasuyuki Takeo
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = io.swagger.v3.oas.annotations.info.Info(
        title = "Todo Service",
        version = "1.0.0",
        description = "Springboot Webflux and Coroutine example implementations"
    )
)
class SpringBootWebfluxKotlinCoroutineApplication {
    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer? {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("db/tables.sql")))
        return initializer
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBootWebfluxKotlinCoroutineApplication>(*args)
}
