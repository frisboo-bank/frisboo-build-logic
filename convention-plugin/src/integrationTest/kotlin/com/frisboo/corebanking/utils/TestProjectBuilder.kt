/*
 * Copyright 2025 Frisboo Bank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.frisboo.corebanking.utils

import org.gradle.internal.impldep.org.eclipse.jgit.api.Git
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.tooling.BuildException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeText

class TestProjectBuilder(
    val projectDir: Path,
) {
    init {
        Files.createDirectories(projectDir)
    }

    fun withVersionCatalog(content: String = defaultVersionCatalog): TestProjectBuilder {
        val catalogDir = projectDir.resolve("gradle")
        Files.createDirectories(catalogDir)
        catalogDir.resolve("libs.versions.toml").writeText(content)
        return this
    }

    fun withSettingsGradle(content: String = defaultSettings): TestProjectBuilder {
        projectDir.resolve("settings.gradle.kts").writeText(content)
        return this
    }

    fun withBuildGradle(content: String): TestProjectBuilder {
        projectDir.resolve("build.gradle.kts").writeText(content)
        return this
    }

    fun withKotlinSource(
        packageName: String = "com.frisboo.corebanking",
        className: String = "KotlinTestClass",
        content: String? = null,
    ): TestProjectBuilder {
        val sourceDir = projectDir.resolve("src/main/kotlin/${packageName.replace('.', '/')}")
        Files.createDirectories(sourceDir)
        val fileContent =
            content
                ?:
                    """
                    package $packageName

                    class $className {
                        fun greet(): String {
                            return "Hello from $className"
                        }
                    }
                    """.trimIndent()
        sourceDir.resolve("$className.kt").writeText(fileContent)
        return this
    }

    fun runGradleAndFail(vararg tasks: String): BuildResult = runGradle(tasks.toList(), expectFailure = true)

    fun runGradle(vararg tasks: String): BuildResult = runGradle(tasks.toList())

    private fun runGradle(
        tasks: List<String>,
        arguments: List<String> = emptyList(),
        expectFailure: Boolean = false,
    ): BuildResult {
        val allArgs =
            buildList {
                addAll(tasks)
                addAll(arguments)
                add("--stacktrace")
            }

        val runner =
            GradleRunner
                .create()
                .withProjectDir(projectDir.toFile())
                .withArguments(allArgs)
                .withPluginClasspath()
                .withDebug(true)

        return try {
            if (expectFailure) runner.buildAndFail() else runner.build()
        } catch (e: BuildException) {
            throw BuildException("Gradle build failed: ${e.message}", e)
        }
    }

    companion object {
        fun create(): TestProjectBuilder {
            val tempDir = Files.createTempDirectory("gradle-test-")
            Git.init().setDirectory(tempDir.toFile()).call().apply {
                commit()
                    .setMessage("Init tests")
                    .setSign(false)
                    .setAllowEmpty(true)
                    .call()
            }
            return TestProjectBuilder(tempDir)
        }

        private val defaultVersionCatalog =
            """
            [versions]
            arrowkt = "2.1.0"
            context-propagation = "1.2.0"
            cpd-plugin = "3.5"
            dependency-analysis-plugin = "3.1.0"
            detekt = "2.0.0-alpha.1"
            detekt-plugin = "2.0.0-alpha.1"
            dokka-plugin = "2.1.0"
            exposed = "1.0.0-rc-3"
            flyway = "11.19.0"
            flyway-plugin = "11.19.0"
            gradles-plugin = "0.53.0"
            grpc = "1.77.0"
            grpc-kotlin-stub = "1.5.0"
            grpc-netty-shaded = "1.77.0"
            grpc-protobuf = "1.77.0"
            h2 = "2.4.240"
            jackson = "2.20.1"
            jackson-annotations = "3.0-rc5"
            jackson-datatype-jsr310 = "3.0.0-rc2"
            jetbrains-annotations = "26.0.2-1"
            jgit = "7.4.0.202509020913-r"
            junit = "6.0.0"
            jvm-target = "24"
            kotest = "6.0.5"
            kotest-assertions-arrow = "2.0.0"
            kotest-property-arbs = "2.1.2"
            kotest-property-datetime = "2.0.0"
            kotlin = "2.2.21"
            kotlin-language = "2.2"
            kotlin-logging = "7.0.13"
            kotlin-plugin = "2.2.21"
            kotlinx-coroutines = "1.10.2"
            kotlinx-datetime = "0.7.1-0.6.x-compat"
            kover = "0.9.3"
            kover-plugin = "0.9.3"
            ktlint = "1.8.0"
            maven-publish-plugin = "0.34.0"
            mockk = "1.14.6"
            mongodb = "5.6.1"
            openapi-generator-plugin = "7.14.0"
            owasp-dependencycheck-plugin = "12.1.9"
            postgresql = "42.7.8"
            protobuf = "4.33.1"
            protobuf-plugin = "0.9.5"
            protoc = "4.33.1"
            protoc-gen-grpc-java = "1.77.0"
            protoc-gen-grpc-kotlin = "1.5.0"
            protovalidate = "1.0.1"
            reactor = "2024.0.11"
            restrict-imports-plugin = "3.0.0"
            spotless-plugin = "8.1.0"
            spring-boot = "3.5.8"
            spring-boot-grpc = "0.12.0"
            spring-boot-plugin = "3.5.8"
            spring-dependency-management-plugin = "1.1.7"
            springdoc-openapi = "2.8.13"
            springdoc-openapi-plugin = "1.9.0"
            test-retry-plugin = "1.6.4"
            testcontainers = "2.0.1"

            [libraries]
            # Kotlin
            kotlin-bom = { group = "org.jetbrains.kotlin", name = "kotlin-bom", version.ref = "kotlin" }
            kotlin-gradle-plugin = { group = "org.jetbrains.kotlin", name = "kotlin-gradle-plugin", version.ref = "kotlin" }
            kotlin-reflect = { group = "org.jetbrains.kotlin", name = "kotlin-reflect" }
            kotlin-test-junit5 = { group = "org.jetbrains.kotlin", name = "kotlin-test-junit5" }
            kotlin-logging = { group = "io.github.oshai", name = "kotlin-logging-jvm", version.ref = "kotlin-logging" }

            # Kotlin Extensions
            kotlinx-coroutines-bom = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-bom", version.ref = "kotlinx-coroutines" }
            kotlinx-coroutines-core = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core" }
            kotlinx-coroutines-debug = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-debug" }
            kotlinx-coroutines-reactor = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-reactor" }
            kotlinx-coroutines-test = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-test" }
            kotlinx-datetime = { group = "org.jetbrains.kotlinx", name = "kotlinx-datetime", version.ref = "kotlinx-datetime" }

            # ArrowKT
            arrow-kt-bom = { group = "io.arrow-kt", name = "arrow-stack", version.ref = "arrowkt" }
            arrow-kt-core = { group = "io.arrow-kt", name = "arrow-core" }
            arrow-kt-coroutines = { group = "io.arrow-kt", name = "arrow-fx-coroutines" }

            # Spring
            spring-boot-autoconfigure = { group = "org.springframework.boot", name = "spring-boot-autoconfigure" }
            spring-boot-bom = { group = "org.springframework.boot", name = "spring-boot-dependencies", version.ref = "spring-boot" }
            spring-boot-configuration-processor = { group = "org.springframework.boot", name = "spring-boot-configuration-processor" }
            spring-boot-devtools = { group = "org.springframework.boot", name = "spring-boot-devtools" }
            spring-boot-kafka = { group = "org.springframework.kafka", name = "spring-kafka" }
            spring-boot-kafka-test = { group = "org.springframework.kafka", name = "spring-kafka-test" }
            spring-boot-kotlin = { group = "org.jetbrains.kotlin.plugin.spring", name = "org.jetbrains.kotlin.plugin.spring.gradle.plugin", version = "2.2.21" }
            spring-boot-starter-actuator = { group = "org.springframework.boot", name = "spring-boot-starter-actuator" }
            spring-boot-starter-data-mongodb = { group = "org.springframework.boot", name = "spring-boot-starter-data-mongodb" }
            spring-boot-starter-data-redis = { group = "org.springframework.boot", name = "spring-boot-starter-data-redis" }
            spring-boot-starter-data-redix-reactive = { group = "org.springframework.boot", name = "spring-boot-starter-data-redis-reactive" }
            spring-boot-starter-test = { group = "org.springframework.boot", name = "spring-boot-starter-test" }
            spring-boot-starter-validation = { group = "org.springframework.boot", name = "spring-boot-starter-validation" }
            spring-boot-starter-webflux = { group = "org.springframework.boot", name = "spring-boot-starter-webflux" }
            spring-boot-testcontainers = { group = "org.springframework.boot", name = "spring-boot-testcontainers" }

            # SpringDoc OpenAPI
            springdoc-openapi-bom = { group = "org.springdoc", name = "springdoc-openapi-bom", version.ref = "springdoc-openapi" }
            springdoc-openapi-starter-webflux-api = { group = "org.springdoc", name = "springdoc-openapi-starter-webflux-api" }
            springdoc-openapi-starter-webflux-ui = { group = "org.springdoc", name = "springdoc-openapi-starter-webflux-ui" }

            # Reactor
            reactor-bom = { group = "io.projectreactor", name = "reactor-bom", version.ref = "reactor" }
            reactor-kotlin-extensions = { group = "io.projectreactor.kotlin", name = "reactor-kotlin-extensions" }
            reactor-test = { group = "io.projectreactor", name = "reactor-test" }

            # Database & Persistence
            exposed-bom = { group = "org.jetbrains.exposed", name = "exposed-bom", version.ref = "exposed" }
            exposed-json = { group = "org.jetbrains.exposed", name = "exposed-json" }
            exposed-kotlin-datetime = { group = "org.jetbrains.exposed", name = "exposed-kotlin-datetime" }
            exposed-spring-boot-starter = { group = "org.jetbrains.exposed", name = "exposed-spring-boot-starter" }
            flyway-core = { group = "org.flywaydb", name = "flyway-core", version.ref = "flyway" }
            flyway-database-mongodb = { group = "org.flywaydb", name = "flyway-database-mongodb", version.ref = "flyway" }
            flyway-database-postgresql = { group = "org.flywaydb", name = "flyway-database-postgresql", version.ref = "flyway" }
            h2 = { group = "com.h2database", name = "h2", version.ref = "h2" }
            postgresql = { group = "org.postgresql", name = "postgresql", version.ref = "postgresql" }
            mongodb = { group = "org.mongodb", name = "mongodb-driver-kotlin-coroutine" }

            # gRPC
            context-propagation = { group = "io.micrometer", name = "context-propagation", version.ref = "context-propagation" }
            grpc-bom = { group = "io.grpc", name = "grpc-bom", version.ref = "grpc" }
            grpc-kotlin-stub = { group = "io.grpc", name = "grpc-kotlin-stub", version.ref = "grpc-kotlin-stub" }
            grpc-netty-shaded = { group = "io.grpc", name = "grpc-netty-shaded", version.ref = "grpc-netty-shaded" }
            grpc-protobuf = { group = "io.grpc", name = "grpc-protobuf" }
            grpc-services = { group = "io.grpc", name = "grpc-services", version.ref = "grpc" }
            grpc-servlet = { group = "io.grpc", name = "grpc-servlet", version = "1.77.0" }
            grpc-stub = { group = "io.grpc", name = "grpc-stub" }
            protobuf-java = { group = "com.google.protobuf", name = "protobuf-java", version.ref = "protobuf" }
            protobuf-java-util = { group = "com.google.protobuf", name = "protobuf-java-util", version.ref = "protobuf" }
            protobuf-kotlin = { group = "com.google.protobuf", name = "protobuf-kotlin", version.ref = "protobuf" }
            spring-boot-grpc-bom = { group = "org.springframework.grpc", name = "spring-grpc-dependencies", version.ref = "spring-boot-grpc" }
            spring-boot-grpc-starter = { group = "org.springframework.grpc", name = "spring-grpc-spring-boot-starter", version.ref = "spring-boot-grpc" }
            spring-boot-grpc-test = { group = "org.springframework.grpc", name = "spring-grpc-test" }

            # Testing
            junit-bom = { group = "org.junit", name = "junit-bom", version.ref = "junit" }
            kotest-assertions-arrow = { group = "io.kotest.extensions", name = "kotest-assertions-arrow", version.ref = "kotest-assertions-arrow" }
            kotest-assertions-core = { group = "io.kotest", name = "kotest-assertions-core", version.ref = "kotest" }
            kotest-bom = { group = "io.kotest", name = "kotest-bom", version.ref = "kotest" }
            kotest-extensions = { group = "io.kotest", name = "kotest-extensions", version.ref = "kotest" }
            kotest-property = { group = "io.kotest", name = "kotest-property" }
            kotest-property-arbs = { group = "io.kotest.extensions", name = "kotest-property-arbs", version.ref = "kotest-property-arbs" }
            kotest-property-datetime = { group = "io.kotest.extensions", name = "kotest-property-datetime", version.ref = "kotest-property-datetime" }
            kotest-runner-junit5 = { group = "io.kotest", name = "kotest-runner-junit5" }
            mockk = { group = "io.mockk", name = "mockk", version.ref = "mockk" }

            # Testcontainers
            testcontainers-bom = { group = "org.testcontainers", name = "testcontainers-bom", version.ref = "testcontainers" }
            testcontainers-junit-jupiter = { group = "org.testcontainers", name = "junit-jupiter" }
            testcontainers-postgresql = { group = "org.testcontainers", name = "postgresql" }
            testcontainers-mongodb = { group = "org.testcontainers", name = "mongodb" }
            testcontainers-kafka = { group = "org.testcontainers", name = "kafka" }
            testcontainers-h2 = { group = "org.testcontainers", name = "h2" }

            # Jackson
            jackson-annotations = { group = "com.fasterxml.jackson.core", name = "jackson-annotations" }
            jackson-bom = { group = "com.fasterxml.jackson", name = "jackson-bom", version.ref = "jackson" }
            jackson-core = { group = "com.fasterxml.jackson.core", name = "jackson-core" }
            jackson-databind = { group = "com.fasterxml.jackson.core", name = "jackson-databind" }
            jackson-datatype-jsr310 = { group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jsr310" }
            jackson-module-kotlin = { group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin" }

            # Miscellaneous
            jetbrains-annotations = { group = "org.jetbrains", name = "annotations", version.ref = "jetbrains-annotations" }
            jgit-gradle-plugin = { group = "org.eclipse.jgit", name = "org.eclipse.jgit", version.ref = "jgit" }
            kover = { group = "org.jetbrains.kotlinx", name = "kover", version.ref = "kover" }

            [plugins]
            # Kotlin
            kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin-plugin" }
            kotlin-spring = { id = "org.jetbrains.kotlin.plugin.spring", version.ref = "kotlin-plugin" }

            # Spring
            spring-boot = { id = "org.springframework.boot", version.ref = "spring-boot-plugin" }
            spring-dependency-management = { id = "io.spring.dependency-management", version.ref = "spring-dependency-management-plugin" }

            # Code Quality
            cpd = { id = "de.aaschmid.cpd", version.ref = "cpd-plugin" }
            dependency-analysis = { id = "com.autonomousapps.dependency-analysis", version.ref = "dependency-analysis-plugin" }
            detekt = { id = "dev.detekt", version.ref = "detekt-plugin" }
            kover = { id = "org.jetbrains.kotlinx.kover", version.ref = "kover-plugin" }
            owasp = { id = "org.owasp.dependencycheck", version.ref = "owasp-dependencycheck-plugin" }
            restrictimports = { id = "de.skuzzle.restrictimports", version.ref = "restrict-imports-plugin" }
            spotless = { id = "com.diffplug.spotless", version.ref = "spotless-plugin" }

            # Documentation
            dokka = { id = "org.jetbrains.dokka", version.ref = "dokka-plugin" }
            openapi-generator = { id = "org.openapi.generator", version.ref = "openapi-generator-plugin" }
            springdoc-openapi = { id = "org.springdoc.openapi-gradle-plugin", version.ref = "springdoc-openapi-plugin" }

            # Database
            flyway = { id = "org.flywaydb.flyway", version.ref = "flyway-plugin" }

            # gRPC & Protobuf
            protobuf = { id = "com.google.protobuf", version.ref = "protobuf-plugin" }

            # Publishing
            maven-publish = { id = "com.vanniktech.maven.publish", version.ref = "maven-publish-plugin" }

            # Build Tools
            gradles = { id = "com.github.ben-manes.versions", version.ref = "gradles-plugin" }
            test-retry = { id = "org.gradle.test-retry", version.ref = "test-retry-plugin" }

            [bundles]
            serialization = ["jackson-annotations",
                "jackson-core",
                "jackson-databind",
                "jackson-datatype-jsr310",
                "jackson-module-kotlin",
            ]
            """.trimIndent()

        private val defaultSettings =
            """
            pluginManagement {
                repositories {
                    mavenLocal()
                }
            }

            rootProject.name = "test-project"

            dependencyResolutionManagement {
                repositories {
                    mavenCentral()
                }
                versionCatalogs {
                    create("baseLibs") {
                        from(files("gradle/libs.versions.toml"))
                    }
                }
            }
            """.trimIndent()
    }
}
