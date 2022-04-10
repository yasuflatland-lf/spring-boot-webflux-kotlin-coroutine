package com.sennproject.springbootwebfluxkotlincoroutine.configurations.flyway

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.Duration

@Configuration(value = "abstractR2dbcConfiguration")
@EnableTransactionManagement
class MysqlConfig : AbstractR2dbcConfiguration() {
    @Value("\${spring.r2dbc.url}")
    private lateinit var url: String

    @Value("\${spring.r2dbc.user}")
    private lateinit var user: String

    @Value("\${spring.r2dbc.password}")
    private lateinit var password: String

    // https://github.com/mirromutth/r2dbc-mysql#programmatic-connection-factory-discovery
    @Bean
    override fun connectionFactory(): ConnectionFactory = ConnectionFactories.get(
        builder()
            .option(DRIVER, "mysql")
            .option(HOST, parse(url).getValue(HOST) as String)
            .option(USER, user)
            .option(PORT, parse(url).getValue(PORT) as Int)  // optional, default 3306
            .option(PASSWORD, password) // optional, default null, null means has no password
            .option(DATABASE, "r2dbc") // optional, default null, null means not specifying the database
            .option(CONNECT_TIMEOUT, Duration.ofSeconds(10)) // optional, default null, null means no timeout
//            .option(
//                Option.valueOf("socketTimeout"),
//                Duration.ofSeconds(4)
//            ) // optional, default null, null means no timeout
//            .option(SSL, true) // optional, default sslMode is "preferred", it will be ignore if sslMode is set
//            .option(Option.valueOf("sslMode"), "verify_identity") // optional, default "preferred"
//            .option(
//                Option.valueOf("sslCa"),
//                "/path/to/mysql/ca.pem"
//            ) // required when sslMode is verify_ca or verify_identity, default null, null means has no server CA cert
//            .option(
//                Option.valueOf("sslCert"),
//                "/path/to/mysql/client-cert.pem"
//            ) // optional, default null, null means has no client cert
//            .option(
//                Option.valueOf("sslKey"),
//                "/path/to/mysql/client-key.pem"
//            ) // optional, default null, null means has no client key
//            .option(
//                Option.valueOf("sslKeyPassword"),
//                "key-pem-password-in-here"
//            ) // optional, default null, null means has no password for client key (i.e. "sslKey")
//            .option(
//                Option.valueOf("tlsVersion"),
//                "TLSv1.3,TLSv1.2,TLSv1.1"
//            ) // optional, default is auto-selected by the server
//            .option(
//                Option.valueOf("sslHostnameVerifier"),
//                "com.example.demo.MyVerifier"
//            ) // optional, default is null, null means use standard verifier
//            .option(
//                Option.valueOf("sslContextBuilderCustomizer"),
//                "com.example.demo.MyCustomizer"
//            ) // optional, default is no-op customizer
//            .option(Option.valueOf("zeroDate"), "use_null") // optional, default "use_null"
//            .option(Option.valueOf("useServerPrepareStatement"), true) // optional, default false
//            .option(Option.valueOf("tcpKeepAlive"), true) // optional, default false
//            .option(Option.valueOf("tcpNoDelay"), true) // optional, default false
//            .option(Option.valueOf("autodetectExtensions"), false) // optional, default false
            .build()
    )

}