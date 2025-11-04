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

import com.frisboo.corebanking.convention.CatalogVersionConstants
import com.frisboo.corebanking.convention.utils.applyBomIfEnabled
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.getVersionOrFail
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import com.frisboo.corebanking.convention.utils.onKotlinEnabled
import com.frisboo.corebanking.convention.utils.pluginIdOrThrow
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
        logger.lifecycle("-----------------------------------------------------")
        logger.lifecycle("Configuring Spring Boot settings for project: ${project.name}")
        logger.lifecycle(" - Enabled: ${ext.enabled.get()}")
        logger.lifecycle(" - Spring Boot version: ${libs.getVersionOrFail(CatalogVersionConstants.Versions.SPRING_BOOT_VERSION)}")
        logger.lifecycle(" - Apply Spring Boot BOM: ${ext.springBootBom.enabled.get()}")
        logger.lifecycle("-----------------------------------------------------")

        if (!ext.enabled.get()) return

        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_BOOT))
        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_DEPENDENCY_MANAGEMENT))

        project.onKotlinEnabled {
            project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.KOTLIN_SPRING))
        }

        project.dependencies {
            applyBomIfEnabled(ext.springBootBom)
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_ACTUATOR))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_HATEOAS))
            add(
                "implementation",
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_VALIDATION),
            )
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_WEBFLUX))

            add("developmentOnly", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_DEVTOOLS))

            add(
                "annotationProcessor",
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_CONFIGURATION_PROCESSOR),
            )

            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_TEST))
            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_SECURITY_TEST))
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
