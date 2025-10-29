package com.frisboo.corebanking.managers.quality

import org.gradle.api.Project

internal fun Project.hasKotlinFiles(): Boolean =
    fileTree("src").matching { it.include("**/*.kt", "**/*.kts") }.files.isNotEmpty()
