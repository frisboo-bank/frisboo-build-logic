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
import com.frisboo.corebanking.convention.extensions.TestingExtension
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import com.frisboo.corebanking.convention.utils.onKotlinEnabled
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class TestingManager(
    private val project: Project,
    private val ext: TestingExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        project.onKotlinEnabled {
            addKotlinTestingDependencies()
        }
    }

    private fun addKotlinTestingDependencies() {
        project.dependencies {
            add(
                "testImplementation",
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_RUNNER_JUNIT5).get(),
            )
            add(
                "testImplementation",
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_ASSERTIONS_CORE).get(),
            )
            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.MOCKK).get())
        }
    }
}
