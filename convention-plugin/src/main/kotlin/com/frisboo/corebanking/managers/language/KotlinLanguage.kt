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

import com.frisboo.corebanking.DependencyConstants
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.getVersionOrFail
import com.frisboo.corebanking.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public class KotlinLanguage(
    private val project: Project,
) {
    private val libs = project.getLibs()
    private val kotlinVersion = libs.getVersionOrFail(DependencyConstants.Versions.KOTLIN_LANGUAGE)
    private val jvmTargetVersion = libs.getVersionOrFail(DependencyConstants.Versions.JVM_TARGET)

    public fun configure() {
        project.plugins.withId(libs.pluginIdOrThrow(DependencyConstants.Plugins.KOTLIN_JVM)) {
            project.configure<KotlinJvmProjectExtension> {
                jvmToolchain(jvmTargetVersion.toInt())

                // Use warnings instead of full explicit API to avoid issues with some generated sources
                explicitApiWarning()
            }

            project.tasks.withType<KotlinCompile>().configureEach { t ->
                t.compilerOptions {
                    apiVersion.set(KotlinVersion.fromVersion(kotlinVersion))
                    languageVersion.set(KotlinVersion.fromVersion(kotlinVersion))
                    jvmTarget.set(JvmTarget.fromTarget(jvmTargetVersion))

                    allWarningsAsErrors.set(warningsAsErrors())
                    progressiveMode.set(progressiveMode())

                    optIn.add("kotlin.RequiresOptIn")
                    optIn.addAll(additionalOptIns())

                    jvmDefault.set(JvmDefaultMode.ENABLE)
                    freeCompilerArgs.addAll(listOf("-Xjsr305=strict"))
                }
            }
        }
    }

    private fun warningsAsErrors(): Provider<Boolean> =
        project.providers.gradleProperty("warningsAsErrors").map(String::toBoolean).orElse(
            project.providers.environmentVariable("CI").map { true }.orElse(false),
        )

    private fun progressiveMode(): Provider<Boolean> =
        project.providers.gradleProperty("kotlin.progressive").map(String::toBoolean).orElse(false)

    // Hook up an extension-backed list later if needed
    private fun additionalOptIns(): List<String> = emptyList()
}
