import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    jacoco
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
    id("org.openapi.generator") version "7.10.0"

    kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
}

group = "com.sennproject"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

extra["kotestVersion"] = "5.9.1"
extra["openAPIVersion"] = "1.8.0"
extra["testcontainersVersion"] = "1.20.4"
extra["coroutinesCoreVersion"] = "1.10.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("coroutinesCoreVersion")}")
    implementation("org.springframework:spring-jdbc")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    // OpenAPI
    implementation("org.springdoc:springdoc-openapi-kotlin:${property("openAPIVersion")}")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:${property("openAPIVersion")}")

    // Database
    implementation("io.asyncer:r2dbc-mysql:1.3.1")
    implementation("io.r2dbc:r2dbc-pool:1.0.2.RELEASE")
    runtimeOnly("com.mysql:mysql-connector-j:8.4.+")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.data:spring-data-relational")

    // Test
    testImplementation("io.kotest:kotest-runner-junit5:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-property:${property("kotestVersion")}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${property("coroutinesCoreVersion")}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter:1.17.3")
    testImplementation("org.testcontainers:mysql:1.17.3")
    testImplementation("org.testcontainers:r2dbc")

    // Faker
    implementation("net.datafaker:datafaker:1.8.1")

    // Netty native libraries for MacOS
    if (System.getProperty("os.name").lowercase().contains("mac")) {
        implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
        implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-x86_64")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "21"
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
