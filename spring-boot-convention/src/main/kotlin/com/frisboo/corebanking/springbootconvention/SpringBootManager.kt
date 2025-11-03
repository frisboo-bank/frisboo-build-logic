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
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import com.frisboo.corebanking.convention.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.springframework.boot.gradle.dsl.SpringBootExtension as SpringBootDslExtension

public class SpringBootManager(
    private val project: Project,
    private val ext: SpringBootExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        println("--- Configuring Spring Boot Manager ---")
        println(" - Enabled: ${ext.enabled.get()}")
        println("---------------------------------------")

        if (!ext.enabled.get()) return

        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_BOOT))
        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_DEPENDENCY_MANAGEMENT))

        project.plugins.withId(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.KOTLIN_JVM)) {
            project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.KOTLIN_SPRING))
        }

        project.dependencies {
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_ACTUATOR))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_HATEOAS))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_VALIDATION))
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
    }
}
