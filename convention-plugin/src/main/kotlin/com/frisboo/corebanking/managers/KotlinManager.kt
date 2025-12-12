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
import com.frisboo.corebanking.extensions.KotlinExtension
import com.frisboo.corebanking.utils.addCompileOnly
import com.frisboo.corebanking.utils.addImplementation
import com.frisboo.corebanking.utils.addTestImplementation
import com.frisboo.corebanking.utils.addTestRuntimeOnly
import com.frisboo.corebanking.utils.applyBomIfEnabled
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.dependencies

public class KotlinManager(
    private val project: Project,
    private val ext: KotlinExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        project.dependencies {
            applyBomIfEnabled(
                ext.arrowKtBom,
                ext.kotlinBom,
                ext.kotlinxCoroutinesBom,
                ext.reactiveBom,
            )

            arrayOf(
                CatalogVersionConstants.Libraries.ARROW_KT_CORE,
                CatalogVersionConstants.Libraries.ARROW_KT_COROUTINES,
                CatalogVersionConstants.Libraries.JACKSON_MODULE_KOTLIN,
                CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_CORE,
                CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_REACTOR,
                CatalogVersionConstants.Libraries.KOTLIN_LOGGING,
                CatalogVersionConstants.Libraries.KOTLIN_REFLECT,
                CatalogVersionConstants.Libraries.REACTOR_KOTLIN_EXTENSIONS,
            ).forEach { addImplementation(libs.libraryOrThrow(it)) }

            addCompileOnly(libs.libraryOrThrow(CatalogVersionConstants.Libraries.JETBRAINS_ANNOTATIONS))

            // Test
            arrayOf(
                CatalogVersionConstants.Libraries.KOTEST_ASSERTIONS_CORE,
                CatalogVersionConstants.Libraries.KOTEST_ASSERTIONS_ARROW,
                CatalogVersionConstants.Libraries.KOTEST_PROPERTY,
                CatalogVersionConstants.Libraries.KOTEST_PROPERTY_ARBS,
                CatalogVersionConstants.Libraries.KOTEST_PROPERTY_DATETIME,
                CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_DEBUG,
                CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_TEST,
                CatalogVersionConstants.Libraries.KOTLIN_TEST_JUNIT5,
                CatalogVersionConstants.Libraries.MOCKK,
                CatalogVersionConstants.Libraries.REACTOR_TEST,
            ).forEach { addTestImplementation(libs.libraryOrThrow(it)) }

            addTestRuntimeOnly(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_RUNNER_JUNIT5))
        }
    }
}
