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
package com.frisboo.corebanking.managers.quality

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import com.diffplug.spotless.LineEnding
import com.frisboo.corebanking.extensions.QualityExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless(
    ext: QualityExtension,
    libs: VersionCatalog,
) {
    if (!ext.spotless.enabled.get()) return

    pluginManager.apply(SpotlessPlugin::class.java)

    val headerFile = rootProject.layout.projectDirectory.file("config/license-header.txt")
    val editorConfig = rootProject.layout.projectDirectory.file(".editorconfig")
    val ktlintVersion = libs.findVersion("ktlint").get().requiredVersion
    val delimiter =
        "^\\s*(plugins|pluginManagement|import|buildscript|" +
            "dependencyResolutionManagement|enableFeaturePreview|include|rootProject)\\b"
    val commonExcludes =
        listOf(
            "**/.gradle/**",
            "**/.idea/**",
            "**/.git/**",
            "**/.gradle-test-kit/**",
        )
    val generatedDir =
        project.layout.buildDirectory
            .dir("generated")
            .get()
            .toString()

    logger.debug("$generatedDir/**/*.kt")

    configure<SpotlessExtension> {
        kotlin { t ->
            t.target("**/*.kt")
            t.targetExclude(commonExcludes)
            t.ktlint(ktlintVersion).setEditorConfigPath(editorConfig)
            t.trimTrailingWhitespace()
            t.endWithNewline()
            t.licenseHeaderFile(headerFile)
            t.lineEndings = LineEnding.UNIX
        }
        kotlinGradle { t ->
            t.target("**/*.gradle.kts")
            t.targetExclude(commonExcludes)
            t.ktlint(ktlintVersion).setEditorConfigPath(editorConfig)
            t.trimTrailingWhitespace()
            t.endWithNewline()
            t.licenseHeaderFile(headerFile, delimiter)
            t.lineEndings = LineEnding.UNIX
        }
        format("misc") { t ->
            t.target(
                "**/*.md",
                "**/*.properties",
                "**/*.yml",
                "**/*.yaml",
                "**/*.xml",
                "**/.gitignore",
                "**/*.txt",
            )
            t.targetExclude(commonExcludes)
            t.trimTrailingWhitespace()
            t.endWithNewline()
            t.lineEndings = LineEnding.UNIX
        }
    }

    tasks.named("check").configure { t ->
        t.dependsOn("spotlessCheck")
    }
}
