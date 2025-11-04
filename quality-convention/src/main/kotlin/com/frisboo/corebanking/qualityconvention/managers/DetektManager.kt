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
package com.frisboo.corebanking.qualityconvention.managers

import com.frisboo.corebanking.convention.utils.hasKotlinSources
import com.frisboo.corebanking.qualityconvention.QualityExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDetekt(
    ext: QualityExtension,
    libs: VersionCatalog,
) {
    if (!ext.detekt.enabled.get() || !hasKotlinSources()) return

    pluginManager.apply(DetektPlugin::class.java)

    val detektConfig = rootProject.layout.projectDirectory.file("config/detekt/detekt.yml")
    val detektBaseline = rootProject.layout.projectDirectory.file("config/detekt/detekt-baseline.xml").asFile

    configure<DetektExtension> {
        autoCorrect = ext.detekt.autoCorrect.get()
        baseline = detektBaseline
        buildUponDefaultConfig = true
        config.setFrom(detektConfig)
        parallel = true
//        toolVersion = libs.getVersionOrFail(CatalogVersionConstants.Versions.DETEKT_VERSION)
    }

    tasks.withType<Detekt>().configureEach { detekt ->
        detekt.reports {
            it.xml.required.set(true)
            it.html.required.set(true)
            it.sarif.required.set(true)
            it.txt.required.set(false)
            it.md.required.set(false)
        }
    }

//    configure<DetektExtension> {
//        toolVersion = libs.getVersionOrFail(CatalogVersionConstants.Versions.DETEKT_VERSION)
//        autoCorrect = ext.detekt.autoCorrect.get()
//        buildUponDefaultConfig = true
//        isIgnoreFailures = !ext.detekt.failOnViolation.get()
//        source.setFrom(files("src/main/kotlin", "src/test/kotlin").plus(ext.detekt.source.get()))
////        config.setFrom(detektConfig)
////        baseline = detektBaseline
//        parallel = true
//    }
//
//    tasks.withType<Detekt>().configureEach { detekt ->
//        detekt.reports {
//            it.xml.required.set(true)
//            it.html.required.set(true)
//            it.sarif.required.set(true)
//            it.txt.required.set(false)
//            it.md.required.set(false)
//        }
//    }
//    tasks.named("check").configure { t ->
//        t.dependsOn("detekt")
//    }
}
