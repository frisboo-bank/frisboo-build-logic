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

plugins {
    `java-gradle-plugin`
    `jvm-test-suite`
    id("kotlin-conventions")
    alias(baseLibs.plugins.maven.publish)
}

description = "Gradle plugin that provides core convention features for core banking apis"

dependencies {
    implementation(plugin(baseLibs.plugins.cpd))
    implementation(plugin(baseLibs.plugins.detekt))
    implementation(plugin(baseLibs.plugins.dokka))
    implementation(plugin(baseLibs.plugins.kover))
    implementation(plugin(baseLibs.plugins.openapi.generator))
    implementation(plugin(baseLibs.plugins.owasp))
    implementation(plugin(baseLibs.plugins.protobuf))
    implementation(plugin(baseLibs.plugins.restrictimports))
    implementation(plugin(baseLibs.plugins.spotless))
    implementation(plugin(baseLibs.plugins.spring.boot))
    implementation(plugin(baseLibs.plugins.springdoc.openapi))
}

val catalogsExtension = extensions.getByType<VersionCatalogsExtension>()
val libExtension: VersionCatalog = catalogsExtension.named("baseLibs")

gradlePlugin {
    website = "https://github.com/jolafrite/frisboo-core-banking"
    vcsUrl = "https://github.com/jolafrite/frisboo-core-banking.git"
    plugins {
        create("coreBankingConvention") {
            id = "com.frisboo.corebanking.gradle-convention-plugin"
            displayName = "Frisboo Core Banking Gradle Convention"
            description = "Gradle plugin that provides conventions for to core banking apis"
            tags = listOf(
                "frisboo",
                "frisboo-core-banking",
                "convention-plugin",
            )
            implementationClass = "com.frisboo.corebanking.CoreBankingConventionPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "FrisbooGitHubPackages"
            url = uri("https://maven.pkg.github.com/jolafrite/frisboo-core-banking")
            credentials {
                username = project.findProperty("frisboo.gpr.user") as String? ?: System.getenv("FRISBOO_GPR_USERNAME")
                password = project.findProperty("frisboo.gpr.key") as String? ?: System.getenv("FRISBOO_GPR_TOKEN")
            }
        }
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
                implementation(gradleTestKit())
                implementation(platform(baseLibs.kotest.bom))
                implementation(platform(baseLibs.kotlinx.coroutines.bom))
                implementation(baseLibs.kotest.assertions.core)
                implementation(baseLibs.kotlinx.coroutines.core)
                implementation(baseLibs.kotlinx.coroutines.test)
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

tasks.register<GenerateCatalogVersionConstants>("generateCatalogVersionConstants") {
    group = "Version Catalog"
    description = "Generates constants for the version catalog"
    namespace = "com.frisboo.corebanking.constants"
    className = "CatalogVersionConstants"
    outputDir = layout.projectDirectory.dir("./src/main/kotlin/com/frisboo/corebanking/constants")

    versionAliases.set(libExtension.versionAliases)
    pluginAliases.set(libExtension.pluginAliases)
    libraryAliases.set(libExtension.libraryAliases)
    bundleAliases.set(libExtension.bundleAliases)
}

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}

fun plugin(plugin: Provider<PluginDependency>): Provider<String> =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
