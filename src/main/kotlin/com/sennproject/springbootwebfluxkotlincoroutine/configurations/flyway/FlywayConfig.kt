package com.sennproject.springbootwebfluxkotlincoroutine.configurations.flyway

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Yasuyuki Takeo
 */
@Configuration
class FlywayConfig() {
    @Value("\${spring.flyway.url}")
    private lateinit var url: String

    @Value("\${spring.flyway.user}")
    private lateinit var user: String

    @Value("\${spring.flyway.password}")
    private lateinit var password: String

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway? {
        return Flyway(
            Flyway.configure()
                .baselineOnMigrate(false)
                .dataSource(url, user, password)
        )
    }
}