package com.frisboo.corebanking.managers

import com.frisboo.corebanking.DependencyConstants
import com.frisboo.corebanking.extensions.OpenapiExtension
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import com.frisboo.corebanking.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class OpenapiManager(
    private val project: Project,
    private val ext: OpenapiExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        if (!ext.enabled.get()) return

        project.pluginManager.apply(libs.pluginIdOrThrow(DependencyConstants.Plugins.OPENAPI_GENERATOR))

        project.plugins.withId(libs.pluginIdOrThrow(DependencyConstants.Plugins.SPRING_BOOT)) {
            configureSpringBoot()
        }

        project.dependencies {}
    }

    private fun configureSpringBoot() {
        project.pluginManager.apply(libs.pluginIdOrThrow(DependencyConstants.Plugins.SPRINGDOC_OPENAPI))

        project.dependencies {
            add("implementation", libs.libraryOrThrow(DependencyConstants.Libraries.SPRINGDOC_OPENAPI_STARTER_WEBFLUX_UI))
        }
    }
}
