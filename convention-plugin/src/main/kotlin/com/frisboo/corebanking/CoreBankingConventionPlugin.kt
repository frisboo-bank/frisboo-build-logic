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
import com.frisboo.corebanking.managers.CachingManager
import com.frisboo.corebanking.managers.GRPCManager
import com.frisboo.corebanking.managers.KotlinManager
import com.frisboo.corebanking.managers.MessagingManager
import com.frisboo.corebanking.managers.OpenapiManager
import com.frisboo.corebanking.managers.PersistenceManager
import com.frisboo.corebanking.managers.QualityManager
import com.frisboo.corebanking.managers.RestrictImportsManager
import com.frisboo.corebanking.managers.SpringBootManager
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

                    if (ext.enableCaching.getOrElse(false)) CachingManager(this, ext.caching).configure()
                    if (ext.enableGRPC.getOrElse(false)) GRPCManager(this, ext.grpc).configure()
                    if (ext.enableMessaging.getOrElse(false)) MessagingManager(this, ext.messaging).configure()
                    if (ext.enableOpenapi.getOrElse(false)) OpenapiManager(this, ext.openapi).configure()
                    if (ext.enablePersistence.getOrElse(false)) PersistenceManager(this, ext.persistence).configure()
                    if (ext.enableQuality.getOrElse(false)) QualityManager(this, ext.quality).configure()
                    if (ext.enableSpringBoot.getOrElse(false)) SpringBootManager(this, ext.springBoot).configure()
                    if (ext.enableTesting.getOrElse(false)) TestingManager(this, ext.testing).configure()
                } catch (e: IllegalStateException) {
                    throw IllegalStateException("Failed to configure Frisboo Core Banking Convention: ${e.message}", e)
                }
            }

            tasks.register("FrisbooCoreBankingConventionInfo") { t ->
                group = "Help"
                description = "Displays information about the Frisboo Core Banking Convention"

                t.doLast {
                    logger.lifecycle("Frisboo Core Banking Convention Applied")
                }
            }
        }
    }
}
