package com.frisboo.corebanking.managers.quality

import com.frisboo.corebanking.extensions.quality.QualityExtension
import com.frisboo.corebanking.utils.hasKotlinSources
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog

internal fun Project.configureDetekt(ext: QualityExtension, libs: VersionCatalog) {
    if (!ext.detekt.enabled.get() || !hasKotlinSources()) return

}
