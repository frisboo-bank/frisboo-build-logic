package com.frisboo.corebanking.kotlinconvention

import com.frisboo.corebanking.convention.CatalogVersionConstants
import com.frisboo.corebanking.convention.utils.applyBomIfEnabled
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import com.frisboo.corebanking.convention.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class KotlinManager(
    private val project: Project,
    private val ext: KotlinExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        project.dependencies {
            applyBomIfEnabled(ext.arrowKtBom)
            applyBomIfEnabled(ext.kotlinBom)
            applyBomIfEnabled(ext.kotlinxCoroutinesBom)

            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.ARROW_KT_CORE))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.ARROW_KT_COROUTINES))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_CORE))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_REACTOR))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_REFLECT))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_LOGGING))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.REACTOR_KOTLIN_EXTENSIONS))

            add("compileOnly", libs.libraryOrThrow(CatalogVersionConstants.Libraries.JETBRAINS_ANNOTATIONS))

            // Test
            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.REACTOR_TEST))
            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLIN_TEST_JUNIT5))
            add("testImplementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_TEST))
            add("testRuntimeOnly", libs.libraryOrThrow(CatalogVersionConstants.Libraries.JUNIT_PLATFORM_LAUNCHER))
        }
    }
}
