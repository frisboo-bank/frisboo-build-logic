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
package com.frisboo.corebanking.convention.managers

import com.frisboo.corebanking.convention.CatalogVersionConstants
import com.frisboo.corebanking.convention.extensions.KotlinExtension
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class KotlinManager(
    private val project: Project,
    private val ext: KotlinExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        project.dependencies {
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.ARROW_KT_CORE))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.ARROW_KT_COROUTINES))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_CORE))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_REACTOR))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_REFLECT))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_LOGGING))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.REACTOR_KOTLIN_EXTENSIONS))

            add("compileOnly", libs.libraryOrThrow(CatalogVersionConstants.Libraries.JETBRAINS_ANNOTATIONS))

            // Test
            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.REACTOR_TEST))
            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_TEST_JUNIT5))
            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_TEST))
            add("testRuntimeOnly", libs.libraryOrThrow(CatalogVersionConstants.Libraries.JUNIT_PLATFORM_LAUNCHER))
        }
    }
}
