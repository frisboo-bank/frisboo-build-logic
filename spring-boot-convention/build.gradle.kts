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
    `jvm-test-suite`
    id("kotlin-conventions")
    alias(libs.plugins.plugin.publish)
    alias(libs.plugins.dokka)
}

description = "Gradle plugin that provides conventions for SpringBoot to core banking apis"

dependencies {
    implementation(project(":core-convention"))
    implementation(plugin(libs.plugins.spring.boot))
    implementation(plugin(libs.plugins.spring.dependency.management))
    implementation(plugin(libs.plugins.kotlin.spring))
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

gradlePlugin {
    plugins {
        create(
            "frisbooCoreBankingSpringBootConvention",
            Action {
                id = "com.frisboo.corebanking.spring-boot-convention"
                displayName = "Frisboo Core Banking SpringBoot Convention"
                description = "Gradle plugin that provides conventions for spring-boot to core banking apis"
                tags =
                    listOf(
                        "frisboo",
                        "frisboo-core-banking",
                        "convention-plugin",
                        "spring-boot",
                    )
                implementationClass = "com.frisboo.corebanking.springbootconvention.SpringBootConvention"
            },
        )
    }
}

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}
