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
package com.frisboo.corebanking.managers.language

import com.frisboo.corebanking.constants.CatalogVersionConstants
import com.frisboo.corebanking.utils.addCompileOnly
import com.frisboo.corebanking.utils.addImplementation
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.getVersionOrFail
import com.frisboo.corebanking.utils.libraryOrThrow
import com.frisboo.corebanking.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public class KotlinLanguage(
    private val project: Project,
) {
    private val libs = project.getLibs()
    private val kotlinVersion = libs.getVersionOrFail(CatalogVersionConstants.Versions.KOTLIN_LANGUAGE)
    private val jvmTargetVersion = libs.getVersionOrFail(CatalogVersionConstants.Versions.JVM_TARGET)

    public fun configure() {
        project.plugins.withId(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.KOTLIN_JVM)) {
            project.configure<KotlinJvmProjectExtension> {
                jvmToolchain(jvmTargetVersion.toInt())
            }

            project.tasks.withType<KotlinCompile>().configureEach { t ->
                t.compilerOptions {
                    apiVersion.set(KotlinVersion.fromVersion(kotlinVersion))
                    languageVersion.set(KotlinVersion.fromVersion(kotlinVersion))
                    jvmTarget.set(JvmTarget.fromTarget(jvmTargetVersion))
                    allWarningsAsErrors.set(true)
                    progressiveMode.set(true)
                    optIn.add("kotlin.RequiresOptIn")
                    freeCompilerArgs.addAll(listOf("-Xjsr305=strict"))
                }
            }

            project.dependencies {
                addImplementation(platform(libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_BOM)))
                addCompileOnly(libs.libraryOrThrow(CatalogVersionConstants.Libraries.JETBRAINS_ANNOTATIONS))
            }
        }
    }
}
