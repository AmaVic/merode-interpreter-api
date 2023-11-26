plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.dokka") version "1.8.20"
    application
}

group = "be.vamaralds"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //Functional Programming - Arrow
    implementation("io.arrow-kt:arrow-core:1.2.0")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.2.0")

    //MXP Parser
    implementation("com.sun.xml.bind:jaxb-impl:2.3.3")
    implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.3")

    //API - Ktor
    implementation("io.ktor:ktor-server-core-jvm:2.2.4")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.2.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.2.4")
    implementation("io.ktor:ktor-server-netty-jvm:2.2.4")

    //Logging
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")

    //Testing
    testImplementation("io.ktor:ktor-server-test-host:2.2.4")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.dokkaHtml {
    outputDirectory.set(rootDir.resolve("docs/"))
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}