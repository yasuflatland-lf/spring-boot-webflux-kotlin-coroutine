import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.freefair.lombok") version "6.4.1"
    id("org.springframework.boot") version "2.6.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.spring") version "1.6.20"
}

group = "com.sennproject"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

extra["kotestVersion"] = "5.1.0"
extra["testcontainersVersion"] = "1.16.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.springframework:spring-jdbc")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    
    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.6")

    // Database
    runtimeOnly("dev.miku:r2dbc-mysql")
    runtimeOnly("mysql:mysql-connector-java")

    // Test
    testImplementation("io.kotest:kotest-runner-junit5:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-property:${property("kotestVersion")}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:elasticsearch")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:r2dbc")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
