package com.frisboo.corebanking.qualityconvention.managers

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import com.diffplug.spotless.LineEnding
import com.frisboo.corebanking.qualityconvention.QualityExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless(
    ext: QualityExtension,
    libs: VersionCatalog,
) {
    if (!ext.spotless.enabled.get()) return

    pluginManager.apply(SpotlessPlugin::class.java)

//    variantResolution("spotless")

    val headerFile = rootProject.layout.projectDirectory.file("config/license-header.txt")
    val editorConfig = rootProject.layout.projectDirectory.file(".editorconfig")
    val ktlintVersion = libs.findVersion("ktlint-version").get().requiredVersion
    val delimiter =
        "^\\s*(plugins|pluginManagement|import|buildscript|" + "dependencyResolutionManagement|enableFeaturePreview|include|rootProject)\\b"
    val commonExcludes = listOf(
        "**/build/**",
        "**/build-*/**",
        "**/.gradle/**",
        "**/.idea/**",
        "**/.git/**",
        "**/generated/**",
        "**/.gradle-test-kit/**",
    )

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
