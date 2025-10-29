package com.frisboo.corebanking.managers.quality

import com.frisboo.corebanking.DependencyConstants
import com.frisboo.corebanking.extensions.quality.QualityExtension
import com.frisboo.corebanking.utils.getVersionOrFail
import com.frisboo.corebanking.utils.hasKotlinSources
import de.aaschmid.gradle.plugins.cpd.Cpd
import de.aaschmid.gradle.plugins.cpd.CpdExtension
import de.aaschmid.gradle.plugins.cpd.CpdPlugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin

internal fun Project.configureCpd(ext: QualityExtension, libs: VersionCatalog) {
    if (!ext.cpd.enabled.get() || !hasKotlinSources()) return

    @Suppress("UnstableApiUsage") project.pluginManager.apply(CpdPlugin::class.java)

    project.configure<CpdExtension> {
        toolVersion = libs.getVersionOrFail(DependencyConstants.Versions.CPD)
        isIgnoreFailures = !ext.cpd.failOnViolation.get()
        minimumTokenCount = ext.cpd.minimumTokenCount.get()
    }

    project.tasks.withType<Cpd>().configureEach { task ->
        task.group = LifecycleBasePlugin.VERIFICATION_GROUP
        task.description = "Runs CPD analysis to detect copy-pasted code."

        task.source = project.files("src/main/kotlin", "src/main/java").plus(ext.cpd.source.get()).asFileTree.matching {
                it.include(
                    "**/*.java",
                    "**/*.groovy",
                    "**/*.kt",
                    "**/*.kts",
                )
            }

        task.reports {
            it.xml.required.set(true)
            it.text.required.set(true)
        }
    }

    project.tasks.named("check").configure { t ->
        t.dependsOn("cpdCheck")
    }
}
