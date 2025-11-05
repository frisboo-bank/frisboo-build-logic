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
package com.frisboo.corebanking.kotlinconvention

import com.frisboo.corebanking.convention.CatalogVersionConstants
import com.frisboo.corebanking.convention.utils.addImplementation
import com.frisboo.corebanking.convention.utils.addTestImplementation
import com.frisboo.corebanking.convention.utils.applyBomIfEnabled
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.dependencies

public class KotlinManager(
    private val project: Project,
    private val ext: KotlinExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        logger.debug("-----------------------------------------------------------")
        logger.debug("Configuring Kotlin settings for project ${project.name}")
        logger.debug(" - Arrow KT BOM enabled: ${ext.arrowKtBom.enabled.get()}")
        logger.debug(" - Kotlin BOM enabled: ${ext.kotlinBom.enabled.get()}")
        logger.debug(" - Kotlinx Coroutines BOM enabled: ${ext.kotlinxCoroutinesBom.enabled.get()}")
        logger.debug(" - Reactive BOM enabled: ${ext.reactiveBom.enabled.get()}")
        logger.debug("-----------------------------------------------------------")

        project.dependencies {
            applyBomIfEnabled(ext.arrowKtBom)
            applyBomIfEnabled(ext.kotlinBom)
            applyBomIfEnabled(ext.kotlinxCoroutinesBom)
            applyBomIfEnabled(ext.reactiveBom)

            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.ARROW_KT_CORE))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.ARROW_KT_COROUTINES))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_CORE))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_REACTOR))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_LOGGING))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_REFLECT))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.REACTOR_KOTLIN_EXTENSIONS))

            add("compileOnly", libs.libraryOrThrow(CatalogVersionConstants.Libraries.JETBRAINS_ANNOTATIONS))

            // Test
            addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_ASSERTIONS_CORE))
            addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_RUNNER_JUNIT5))
            addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_TEST))
            addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_TEST_JUNIT5))
            addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.MOCKK))
            addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.REACTOR_TEST))
        }
    }
}
