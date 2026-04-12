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
import com.frisboo.corebanking.extensions.TestingExtension
import com.frisboo.corebanking.utils.addTestImplementation
import com.frisboo.corebanking.utils.addTestRuntimeOnly
import com.frisboo.corebanking.utils.applyBomIfEnabled
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class TestingManager(
    private val project: Project,
    private val ext: TestingExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        project.dependencies {
            applyBomIfEnabled(ext.junitBom)

            if (ext.enableKotest.get()) {
                applyBomIfEnabled(ext.kotestBom)
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_ASSERTIONS_CORE))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_EXTENSIONS))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_PROPERTY))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_PROPERTY_ARBS))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_PROPERTY_DATETIME))
                addTestRuntimeOnly(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_RUNNER_JUNIT5))
            }

            if (ext.enableTestContainers.get()) {
                applyBomIfEnabled(ext.testcontainersBom)
            }

            if (ext.enableMockk.get()) {
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.MOCKK))
            }
        }
    }
}
