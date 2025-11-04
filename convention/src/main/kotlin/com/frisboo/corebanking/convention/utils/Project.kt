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
package com.frisboo.corebanking.convention.utils

import com.frisboo.corebanking.convention.CatalogVersionConstants
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

public fun Project.getLibs(): VersionCatalog =
    extensions.findByType(VersionCatalogsExtension::class.java)?.named("libs")
        ?: error("Version catalog `libs` not found")

public fun Project.hasJavaSources(): Boolean = fileTree("src").matching { it.include("**/*.java") }.files.isNotEmpty()

public fun Project.hasKotlinSources(): Boolean = fileTree("src").matching { it.include("**/*.kt") }.files.isNotEmpty()

public fun Project.onKotlinEnabled(action: Project.() -> Unit): Unit =
    plugins.withId(getLibs().pluginIdOrThrow(CatalogVersionConstants.Plugins.KOTLIN_JVM)) {
        this.action()
    }

public fun Project.onSpringBootEnabled(action: Project.() -> Unit): Unit =
    plugins.withId(getLibs().pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_BOOT)) {
        this.action()
    }
