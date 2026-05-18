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
package com.frisboo.corebanking.managers

import com.frisboo.corebanking.constants.CatalogVersionConstants
import com.frisboo.corebanking.extensions.SpringBootExtension
import com.frisboo.corebanking.utils.addAnnotationProcessor
import com.frisboo.corebanking.utils.addDevelopmentOnly
import com.frisboo.corebanking.utils.addImplementation
import com.frisboo.corebanking.utils.addTestImplementation
import com.frisboo.corebanking.utils.applyBomIfEnabled
import com.frisboo.corebanking.utils.applyCatalogPlugin
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import com.frisboo.corebanking.utils.onKotlinEnabled
import org.gradle.api.Project
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.time.Instant
import org.springframework.boot.gradle.dsl.SpringBootExtension as SpringBootDslExtension

public class SpringBootManager(
    private val project: Project,
    private val ext: SpringBootExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        project.applyCatalogPlugin(CatalogVersionConstants.Plugins.SPRING_DEPENDENCY_MANAGEMENT)
        project.applyCatalogPlugin(CatalogVersionConstants.Plugins.SPRING_BOOT)

        project.dependencies {
            applyBomIfEnabled(
                ext.springBootBom,
                ext.jackson
            )

            if (ext.enableActuator.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_ACTUATOR))
            }
            if (ext.enableValidation.get()) {
                addImplementation(
                    libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_VALIDATION)
                )
            }
            if (ext.enableWebFlux.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_WEBFLUX))
            }

            project.onKotlinEnabled {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_KOTLIN))
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.JACKSON_MODULE_KOTLIN))
            }

            if (ext.enableDevtools.get()) {
                addDevelopmentOnly(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_DEVTOOLS))
            }

            addAnnotationProcessor(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_CONFIGURATION_PROCESSOR)
            )

            if (ext.enableSpringTest.get()) {
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_TEST))
            }
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
                "Build-Timestamp" to Instant.now()
            )
        }
    }
}
