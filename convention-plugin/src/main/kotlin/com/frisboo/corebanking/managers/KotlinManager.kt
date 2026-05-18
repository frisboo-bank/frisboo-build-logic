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
import com.frisboo.corebanking.utils.applyBomIfEnabled
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import org.gradle.api.Project
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
                ext.reactiveBom
            )

            if (ext.enableArrowKt.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.ARROW_KT_CORE))
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.ARROW_KT_COROUTINES))
            }

            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.JACKSON_MODULE_KOTLIN))

            if (ext.enableCoroutines.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_CORE))
            }
            if (ext.enableCoroutinesReactor.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_REACTOR))
            }

            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_LOGGING))

            if (ext.enableKotlinReflect.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_REFLECT))
            }
            if (ext.enableReactorKotlinExtensions.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.REACTOR_KOTLIN_EXTENSIONS))
            }
            if (ext.enableJetbrainsAnnotations.get()) {
                addCompileOnly(libs.libraryOrThrow(CatalogVersionConstants.Libraries.JETBRAINS_ANNOTATIONS))
            }

            // Test — Kotlin-specific test deps only (kotest/mockk owned by TestingManager)
            if (ext.enableArrowKt.get()) {
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTEST_ASSERTIONS_ARROW))
            }
            if (ext.enableCoroutines.get()) {
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_DEBUG))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_TEST))
            }
            addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_TEST_JUNIT5))
            if (ext.enableReactorKotlinExtensions.get()) {
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.REACTOR_TEST))
            }
        }
    }
}
