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

import com.frisboo.corebanking.constants.CatalogVersionConstants
import com.frisboo.corebanking.extensions.QualityExtension
import com.frisboo.corebanking.utils.getVersionOrFail
import com.frisboo.corebanking.utils.hasKotlinSources
import dev.detekt.gradle.Detekt
import dev.detekt.gradle.plugin.DetektPlugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import dev.detekt.gradle.extensions.DetektExtension as DetektExtensionDLS

internal fun Project.configureDetekt(
    ext: QualityExtension,
    libs: VersionCatalog,
) {
    if (!ext.detekt.enabled.get() || !hasKotlinSources()) return

    pluginManager.apply(DetektPlugin::class.java)

    configure<DetektExtensionDLS> {
        toolVersion.set(libs.getVersionOrFail(CatalogVersionConstants.Versions.DETEKT))
        autoCorrect.set(ext.detekt.autoCorrect.get())
        buildUponDefaultConfig.set(true)
        ignoreFailures.set(!ext.detekt.failOnViolation.get())
        source.setFrom(
            files(
                "src/main/kotlin",
                "src/test/kotlin",
                "src/testFixtures/kotlin",
            ).plus(ext.detekt.source.get()),
        )
//        config.setFrom(detektConfig)
//        baseline = detektBaseline
        parallel.set(true)
    }

    tasks.withType<Detekt>().configureEach { detekt ->
        detekt.reports {
            it.html.required.set(true)
            it.sarif.required.set(true)
        }
    }
    tasks.named("check").configure { t ->
        t.dependsOn("detekt")
    }
}
