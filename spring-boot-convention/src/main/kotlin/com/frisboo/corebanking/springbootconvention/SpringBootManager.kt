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
package com.frisboo.corebanking.springbootconvention

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.addAnnotationProcessor
import com.frisboo.corebanking.coreconvention.utils.addDevelopmentOnly
import com.frisboo.corebanking.coreconvention.utils.addImplementation
import com.frisboo.corebanking.coreconvention.utils.addTestImplementation
import com.frisboo.corebanking.coreconvention.utils.applyBomIfEnabled
import com.frisboo.corebanking.coreconvention.utils.getLibs
import com.frisboo.corebanking.coreconvention.utils.getVersionOrFail
import com.frisboo.corebanking.coreconvention.utils.libraryOrThrow
import com.frisboo.corebanking.coreconvention.utils.onKotlinEnabled
import com.frisboo.corebanking.coreconvention.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.dsl.SpringBootExtension as SpringBootDslExtension

public class SpringBootManager(
    private val project: Project,
    private val ext: SpringBootExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        logger.debug("-----------------------------------------------------------")
        logger.debug("Configuring Spring Boot settings for project ${project.name}")
        logger.debug(" - Spring Boot BOM Enabled: ${ext.springBootBom.enabled.get()}")
        logger.debug(" - Jackson BOM Enabled: ${ext.jackson.enabled.get()}")
        logger.debug(
            " - Spring Boot Version: ${libs.getVersionOrFail(CatalogVersionConstants.Versions.SPRING_BOOT_VERSION)}",
        )
        logger.debug("-----------------------------------------------------------")

        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_BOOT))
        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_DEPENDENCY_MANAGEMENT))

        project.onKotlinEnabled {
            project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.KOTLIN_SPRING))
        }

        project.dependencies {
            applyBomIfEnabled(ext.springBootBom)
            applyBomIfEnabled(ext.jackson)

            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_ACTUATOR))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_HATEOAS))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_VALIDATION))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_WEBFLUX))

            project.onKotlinEnabled {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.JACKSON_MODULE_KOTLIN))
            }

            addDevelopmentOnly(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_DEVTOOLS))

            addAnnotationProcessor(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_CONFIGURATION_PROCESSOR),
            )

            addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_TEST))
        }

        project.extensions.configure<SpringBootDslExtension> {
            buildInfo()
        }

        project.tasks.named<BootJar>("bootJar") {
            archiveFileName.set("${project.name}-${project.version}.jar")
            manifest.attributes(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Built-By" to "Frisboo Core Banking",
                "Build-Timestamp" to java.time.Instant.now(),
            )
        }
    }
}
