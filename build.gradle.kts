plugins {
    java
    jacoco
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"

    kotlin("jvm") version "2.4.20"
	kotlin("plugin.spring") version "2.4.10"
}

group = "com.sennproject"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

extra["kotestVersion"] = "6.2.4"
extra["openAPIVersion"] = "3.1.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.springframework:spring-jdbc")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:${property("openAPIVersion")}")

    // Database
    implementation("io.r2dbc:r2dbc-pool")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.data:spring-data-relational")
    implementation("org.postgresql:r2dbc-postgresql")

    // Test
    testImplementation("io.kotest:kotest-runner-junit5:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-table:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-property:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-extensions-spring:${property("kotestVersion")}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

    testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-r2dbc-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testImplementation("org.testcontainers:testcontainers-r2dbc")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Faker
    implementation("net.datafaker:datafaker:2.7.0")

    // Netty native libraries for MacOS
    if (System.getProperty("os.name").lowercase().contains("mac")) {
        // Version comes from the Spring Boot BOM so the natives stay in lockstep
        // with the rest of Netty; they are one artifact set and must not diverge.
        implementation("io.netty:netty-resolver-dns-native-macos::osx-aarch_64")
        implementation("io.netty:netty-resolver-dns-native-macos::osx-x86_64")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
