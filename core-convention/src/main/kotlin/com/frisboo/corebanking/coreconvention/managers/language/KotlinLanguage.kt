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
package com.frisboo.corebanking.coreconvention.managers.language

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.getLibs
import com.frisboo.corebanking.coreconvention.utils.getVersionOrFail
import com.frisboo.corebanking.coreconvention.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.internal.cc.base.logger
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
    private val kotlinVersion = libs.getVersionOrFail(CatalogVersionConstants.Versions.KOTLIN_LANGUAGE_VERSION)
    private val jvmTargetVersion = libs.getVersionOrFail(CatalogVersionConstants.Versions.JVM_TARGET_VERSION)

    public fun configure() {
        logger.debug("-----------------------------------------------------")
        logger.debug("Configuring Kotlin Language settings for project: {}", project.name)
        logger.debug(" - Kotlin version: {}", kotlinVersion)
        logger.debug(" - JVM Target version: {}", jvmTargetVersion)
        logger.debug(" - Warnings as Errors: {}", warningsAsErrors().get())
        logger.debug(" - Progressive Mode: {}", progressiveMode().get())
        logger.debug(" - Additional Opt-Ins: {}", additionalOptIns().joinToString())
        logger.debug("-----------------------------------------------------")

        project.plugins.withId(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.KOTLIN_JVM)) {
            project.configure<KotlinJvmProjectExtension> {
                jvmToolchain(jvmTargetVersion.toInt())

                // Use warnings instead of full explicit API to avoid issues with some generated sources
                explicitApiWarning()
            }

            project.tasks.withType<KotlinCompile>().configureEach { t ->
                t.compilerOptions {
                    // Set the Kotlin API and language versions
                    apiVersion.set(KotlinVersion.fromVersion(kotlinVersion))
                    languageVersion.set(KotlinVersion.fromVersion(kotlinVersion))
                    jvmTarget.set(JvmTarget.fromTarget(jvmTargetVersion))

                    // Treat all warnings as errors
                    allWarningsAsErrors.set(warningsAsErrors())

                    // Enable progressive mode for the compiler
                    progressiveMode.set(progressiveMode())

                    // Opt-in to experimental Kotlin features
                    optIn.add("kotlin.RequiresOptIn")
                    optIn.add("kotlin.time.ExperimentalTime")

                    // Opt-in to common coroutine experimental APIs
                    optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
                    optIn.add("kotlinx.coroutines.FlowPreview")

                    // Enable default methods in Kotlin interfaces
                    jvmDefault.set(JvmDefaultMode.ENABLE)

                    // Add additional compiler arguments
                    freeCompilerArgs.addAll(
                        listOf(
                            "-Xlambdas=indy", // Improve lambda performance on modern JVMs (requires Java 11+)
                            "-Xjsr305=strict", // Enable strict nullability checks for Java interop
                            "-Xannotation-default-target=param-property", // Set default annotation targets
                        ),
                    )
                }
            }
        }
    }

    private fun warningsAsErrors(): Provider<Boolean> =
        project.providers.gradleProperty("warningsAsErrors").map(String::toBoolean).orElse(
            project.providers
                .environmentVariable("CI")
                .map { true }
                .orElse(false),
        )

    private fun progressiveMode(): Provider<Boolean> =
        project.providers
            .gradleProperty("kotlin.progressive")
            .map(String::toBoolean)
            .orElse(false)

    // Hook up an extension-backed list later if needed
    private fun additionalOptIns(): List<String> = emptyList()
}
