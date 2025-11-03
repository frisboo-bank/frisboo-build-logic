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
}

description = "Gradle plugin that provides conventions for Messaging core banking apis"

dependencies {
    api(project(":convention"))
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

gradlePlugin {
    plugins {
        create(
            "frisbooCoreBankingMessagingConvention",
            Action {
                id = "com.frisboo.corebanking.messaging-convention"
                displayName = "Frisboo Core Banking Messaging Convention"
                description = "Gradle plugin that provides conventions for messaging core banking apis"
                tags =
                    listOf(
                        "frisboo",
                        "frisboo-core-banking",
                        "convention-plugin",
                        "messaging",
                    )
                implementationClass = "com.frisboo.corebanking.messagingconvention.MessagingConvention"
            },
        )
    }
}

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}
