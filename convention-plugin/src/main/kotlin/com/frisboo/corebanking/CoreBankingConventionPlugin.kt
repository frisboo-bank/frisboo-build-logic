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
package com.frisboo.corebanking

import com.frisboo.corebanking.constants.PluginMetadata.EXTENSION_NAME
import com.frisboo.corebanking.constants.PluginMetadata.PUBLIC_REPO_URL
import com.frisboo.corebanking.extensions.PluginExtension
import com.frisboo.corebanking.managers.GRPCManager
import com.frisboo.corebanking.managers.KotlinManager
import com.frisboo.corebanking.managers.MessagingManager
import com.frisboo.corebanking.managers.OpenapiManager
import com.frisboo.corebanking.managers.PersistenceManager
import com.frisboo.corebanking.managers.QualityManager
import com.frisboo.corebanking.managers.RestrictImportsManager
import com.frisboo.corebanking.managers.SpringBootManager
import com.frisboo.corebanking.managers.TelemetryManager
import com.frisboo.corebanking.managers.TestingManager
import com.frisboo.corebanking.managers.language.JavaLanguage
import com.frisboo.corebanking.managers.language.KotlinLanguage
import com.frisboo.corebanking.utils.GradleVersionUtils
import com.frisboo.corebanking.utils.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.repositories

public class CoreBankingConventionPlugin : Plugin<Project> {
    /**
     * Applies the Frisboo Core Banking Convention to the given [project].
     *
     * This method configures the project with a set of predefined conventions and settings
     * tailored for Frisboo Core Banking applications. It sets up repositories, applies language
     * configurations, and initializes various managers for features like gRPC, Kotlin, messaging,
     * OpenAPI, persistence, quality checks, import restrictions, Spring Boot, telemetry, and testing.
     *
     * @param project The Gradle project to which the convention will be applied.
     * @throws IllegalStateException if any configuration step fails.
     */
    override fun apply(project: Project) {
        with(project) {
            GradleVersionUtils.checkGradleVersion()

            group = "com.frisboo.corebanking"

            repositories {
                mavenCentral()
                gradlePluginPortal()
                maven { mvn ->
                    mvn.name = "FrisbooGitHubPackages"
                    mvn.url = uri(PUBLIC_REPO_URL)
                    mvn.credentials {
                        it.username = providers.gradleProperty("frisboo.gpr.user").orNull
                            ?: System.getenv("FRISBOO_GPR_USERNAME")
                        it.password =
                            providers.gradleProperty("frisboo.gpr.key").orNull ?: System.getenv("FRISBOO_GPR_TOKEN")
                    }
                }
                mavenLocal()
            }

            val libs = getLibs()
            val ext = extensions.create<PluginExtension>(EXTENSION_NAME, libs)

            JavaLanguage(this).configure()
            KotlinLanguage(this).configure()

            project.afterEvaluate {
                try {
                    KotlinManager(this, ext.kotlin).configure()
                    RestrictImportsManager(this, ext.restrictImports).configure()

                    listOf(
                        ext.enableGRPC to { GRPCManager(this, ext.grpc).configure() },
                        ext.enableMessaging to { MessagingManager(this, ext.messaging).configure() },
                        ext.enableOpenapi to { OpenapiManager(this, ext.openapi).configure() },
                        ext.enablePersistence to { PersistenceManager(this, ext.persistence).configure() },
                        ext.enableQuality to { QualityManager(this, ext.quality).configure() },
                        ext.enableSpringBoot to { SpringBootManager(this, ext.springBoot).configure() },
                        ext.enableTelemetry to { TelemetryManager(this, ext.telemetry).configure() },
                        ext.enableTesting to { TestingManager(this, ext.testing).configure() },
                    ).forEach { (flag, configure) ->
                        flag.getOrElse(false).takeIf { it }?.let { configure() }
                    }
                } catch (e: IllegalStateException) {
                    error("Failed to configure Frisboo Core Banking Convention: ${e.message}")
                }
            }

            tasks.register("FrisbooCoreBankingConventionInfo") { t ->
                group = "Help"
                description = "Displays information about the Frisboo Core Banking Convention"

                val version =
                    project.provider {
//                        extensions.getByType<PluginExtension>().pluginVersion.orNull
                    }

                t.doLast {
                    println("Frisboo Core Banking Convention Applied: $version")
                }
            }
        }
    }
}
