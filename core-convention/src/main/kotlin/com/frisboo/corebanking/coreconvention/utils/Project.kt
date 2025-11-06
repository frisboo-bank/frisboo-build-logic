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
package com.frisboo.corebanking.coreconvention.utils

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

/**
 * Retrieves the `libs` version catalog from the project's extensions.
 *
 * @return The `libs` version catalog.
 * @throws IllegalStateException if the `libs` version catalog is not found.
 */
public fun Project.getLibs(): VersionCatalog =
    extensions.findByType(VersionCatalogsExtension::class.java)?.named("libs")
        ?: error("Version catalog `libs` not found")

/**
 * Applies a plugin to the project using the specified plugin ID.
 *
 * @param pluginId The ID of the plugin to apply.
 */
public fun Project.applyPlugin(pluginId: String) {
    project.pluginManager.apply(pluginId)
}

/**
 * Applies a plugin to the project conditionally based on the provided assertion.
 *
 * @param assert A boolean value; if `true`, the plugin is applied.
 * @param pluginId The ID of the plugin to apply.
 */
public fun Project.applyPluginIf(
    assert: Boolean,
    pluginId: String,
) {
    if (!assert) {
        logger.lifecycle("Skipping plugin: $pluginId")
        return
    }

    logger.lifecycle("Applying plugin: $pluginId")
    project.applyPlugin(pluginId)
}

/**
 * Checks if the project contains Java source files.
 *
 * @return `true` if Java source files are present, `false` otherwise.
 */
public fun Project.hasJavaSources(): Boolean = fileTree("src").matching { it.include("**/*.java") }.files.isNotEmpty()

/**
 * Checks if the project contains Kotlin source files.
 *
 * @return `true` if Kotlin source files are present, `false` otherwise.
 */
public fun Project.hasKotlinSources(): Boolean = fileTree("src").matching { it.include("**/*.kt") }.files.isNotEmpty()

/**
 * Executes the provided action if the Kotlin JVM plugin is enabled in the project.
 *
 * @param action A lambda function to execute when the Kotlin JVM plugin is enabled.
 */
public inline fun Project.onKotlinEnabled(crossinline action: Project.() -> Unit): Unit =
    plugins.withId(getLibs().pluginIdOrThrow(CatalogVersionConstants.Plugins.KOTLIN_JVM)) {
        this.action()
    }

/**
 * Executes the provided action if the Spring Boot plugin is enabled in the project.
 *
 * @param action A lambda function to execute when the Spring Boot plugin is enabled.
 */
public inline fun Project.onSpringBootEnabled(crossinline action: Project.() -> Unit): Unit =
    plugins.withId(getLibs().pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_BOOT)) {
        this.action()
    }
