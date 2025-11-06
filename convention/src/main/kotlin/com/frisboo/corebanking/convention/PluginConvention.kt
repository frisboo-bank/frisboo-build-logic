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
package com.frisboo.corebanking.convention

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.applyPlugin
import com.frisboo.corebanking.coreconvention.utils.getLibs
import com.frisboo.corebanking.coreconvention.utils.getVersionOrFail
import com.frisboo.corebanking.coreconvention.utils.pluginIdOrThrow
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.repositories

public class PluginConvention : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            repositories {
                mavenCentral()
                gradlePluginPortal()
                mavenLocal()
            }

            val libs = getLibs()
            extensions.create<PluginExtension>("coreBankingConvention", libs)

            try {
                project.applyPlugin(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_CORE_CONVENTION))
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_KOTLIN_CONVENTION),
                )
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_GRPC_CONVENTION),
                )
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_MESSAGING_CONVENTION),
                )
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_OPENAPI_CONVENTION),
                )
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_PERSISTENCE_CONVENTION),
                )
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_QUALITY_CONVENTION),
                )
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_SPRINGBOOT_CONVENTION),
                )
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_TELEMETRY_CONVENTION),
                )
                project.applyPlugin(
                    libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.COREBANKING_TESTING_CONVENTION),
                )
            } catch (e: IllegalStateException) {
                error("Failed to configure Frisboo Core Banking Convention: ${e.message}")
            }

            tasks.register("frisbooCoreBankingConventionInfo") { t ->
                group = "Help"
                description = "Displays information about the Frisboo Core Banking Convention"

                val version = libs.getVersionOrFail("frisboo-corebanking-version")

                t.doLast {
                    println("Frisboo Core Banking Convention Applied: $version")
                }
            }
        }
}
