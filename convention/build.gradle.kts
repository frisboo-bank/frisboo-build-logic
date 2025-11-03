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
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    `jvm-test-suite`
    id("kotlin-conventions")
    alias(libs.plugins.plugin.publish)
}

description = "Gradle plugin that provides conventions for core banking apis"

dependencies {
    implementation(libs.restrict.imports.plugin)
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

gradlePlugin {
    plugins {
        create(
            "frisbooConvention",
            Action {
                id = "com.frisboo.corebanking.convention"
                displayName = "Frisboo Core Banking Convention"
                description = "Gradle plugin that provides conventions for core banking apis"
                tags = listOf(
                    "frisboo",
                    "frisboo-core-banking",
                    "convention-plugin",
                )
                implementationClass = "com.frisboo.corebanking.convention.Convention"
            },
        )
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }

        register<JvmTestSuite>("integrationTest") {

            useJUnitJupiter()

            dependencies {
                implementation(project())
                implementation(gradleTestKit())
                implementation(libs.kotest.assertions.core)
            }

            targets.configureEach {
                testTask.configure {

                    classpath += files(tasks.named("pluginUnderTestMetadata"))

                    shouldRunAfter(tasks.named("test"))

                    testLogging {
                        events("passed", "skipped", "failed")
                        exceptionFormat = TestExceptionFormat.FULL
                    }

                    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
                }
            }
        }
    }
}

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}

val catalogsExtension = extensions.getByType<VersionCatalogsExtension>()
val libExtension = catalogsExtension.named("libs")

tasks.register<GenerateCatalogVersionConstants>("generateCatalogVersionConstants") {
    group = "Version Catalog"
    description = "Generates constants for the version catalog"
    namespace = "com.frisboo.corebanking.convention"
    className = "CatalogVersionConstants"
    outputDir = layout.projectDirectory.dir("./src/main/kotlin/com/frisboo/corebanking/convention")

    versionAliases.set(libExtension.versionAliases)
    pluginAliases.set(libExtension.pluginAliases)
    libraryAliases.set(libExtension.libraryAliases)
    bundleAliases.set(libExtension.bundleAliases)
}

// tasks.named("compileKotlin") {
//    dependsOn("generateCatalogVersionConstants")
// }
