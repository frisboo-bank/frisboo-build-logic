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

import com.frisboo.corebanking.convention.extensions.PluginExtension
import com.frisboo.corebanking.convention.managers.KotlinManager
import com.frisboo.corebanking.convention.managers.RestrictImportsManager
import com.frisboo.corebanking.convention.managers.language.JavaLanguage
import com.frisboo.corebanking.convention.managers.language.KotlinLanguage
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.getVersionOrFail
import com.frisboo.corebanking.convention.utils.pluginIdOrThrow
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.repositories

public class Convention : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        repositories {
            mavenCentral()
            gradlePluginPortal()
            mavenLocal()
        }

        val libs = getLibs()
        val ext = extensions.create<PluginExtension>("coreBankingConvention", libs)

        JavaLanguage(this).configure()
        KotlinLanguage(this).configure()

        project.afterEvaluate {
            try {
                KotlinManager(this, ext.kotlin).configure()
                RestrictImportsManager(this, ext).configure()

//                project.pluginManager
//                    .apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.FRISBOO_COREBANKING_GRPC_CONVENTION))
//                project.pluginManager.apply(CatalogVersionConstants.Plugins.FRISBOO_COREBANKING_MESSAGING_CONVENTION)
//                project.pluginManager.apply(CatalogVersionConstants.Plugins.FRISBOO_COREBANKING_OPENAPI_CONVENTION)
//                project.pluginManager.apply(CatalogVersionConstants.Plugins.FRISBOO_COREBANKING_OPENAPI_CONVENTION)
//                project.pluginManager.apply(CatalogVersionConstants.Plugins.FRISBOO_COREBANKING_OPENAPI_CONVENTION)
//                project.pluginManager.apply(CatalogVersionConstants.Plugins.FRISBOO_COREBANKING_OPENAPI_CONVENTION)
//                project.pluginManager.apply(CatalogVersionConstants.Plugins.FRISBOO_COREBANKING_OPENAPI_CONVENTION)
//                BomManager(this, ext.bom).configure()
//                TestingManager(this, ext.testing).configure()
            } catch (e: IllegalStateException) {
                error("Failed to configure Frisboo Core Banking Convention: ${e.message}")
            }
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
