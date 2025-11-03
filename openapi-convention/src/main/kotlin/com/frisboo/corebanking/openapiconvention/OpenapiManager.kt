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
package com.frisboo.corebanking.openapiconvention

import com.frisboo.corebanking.convention.CatalogVersionConstants
import com.frisboo.corebanking.convention.utils.applyBomIfEnabled
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import com.frisboo.corebanking.convention.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorValidateExtension

public class OpenapiManager(
    private val project: Project,
    private val ext: OpenapiExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        println("--- Configuring OpenAPI Manager ---")
        println(" - Enabled: ${ext.enabled.get()}")
        println("-----------------------------------")

        if (!ext.enabled.get()) return

        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.OPENAPI_GENERATOR))

//        project.plugins.withId(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRING_BOOT)) {
//            configureSpringBoot()
//        }
    }

    private fun configureSpringBoot() {
        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRINGDOC_OPENAPI))

        project.dependencies {
            applyBomIfEnabled(ext.springdocOpenapiBom)
            add(
                "implementation",
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRINGDOC_OPENAPI_STARTER_WEBFLUX_UI),
            )
            add(
                "implementation",
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRINGDOC_OPENAPI_STARTER_WEBFLUX_API),
            )
        }

        val generatedInputSpec =
            ext.inputDir.zip(ext.schemaFilename) { dir, filename -> dir.file(filename).asFile.absolutePath }

        project.extensions.configure<OpenApiGeneratorGenerateExtension> {
            generatorName.set("kotlin-spring")
            inputSpec.set(generatedInputSpec)
            outputDir.set(
                ext.outputDir.get().asFile.absolutePath,
            )
            packageName.set(ext.packageName)
            id.set("openapi-documentation")

            // Generation control
            generateApiTests.set(ext.generateApiTests)
            generateModelTests.set(ext.generateModelTests)
            generateApiDocumentation.set(ext.generateApiDocumentation)
            generateModelDocumentation.set(ext.generateModelDocumentation)

            // Build info
            groupId.set(ext.groupId)
            version.set(ext.artifactVersion)

            globalProperties.set(
                mapOf(
                    "apis" to if (ext.generateApis.get()) "" else "false",
                    "models" to if (ext.generateModels.get()) "" else "false",
                ),
            )

            configOptions.set(
                mapOf(
                    "useSpringBoot3" to "true",
                    "useSwaggerUI" to "false",
                    "useTags" to "true",
                    "library" to "spring-boot",
                    "reactive" to "true",
                    "delegatePattern" to "true",
                    "useCoroutines" to "true",
                    "templateDir" to "src/main/resources/openapi-templates/kotlin-spring",
                ),
            )
        }

        // Configure validation
        project.extensions.configure<OpenApiGeneratorValidateExtension> {
            inputSpec.set(generatedInputSpec)
            recommend.set(ext.recommend)
        }

        project.extensions.configure<SourceSetContainer> {
            named("main") {
                it.java.setSrcDirs(
                    listOf(
                        ext.outputDir.get().file("src/main/java"),
                        ext.outputDir.get().file("src/main/kotlin"),
                    ),
                )
            }
        }

        // Clean task for generated sources
        val cleanOpenApi = project.tasks.register<Delete>("cleanOpenApi") {
            group = "build"
            description = "Cleans generated OpenAPI sources"
            delete(ext.outputDir)
        }
        project.tasks.named("clean").configure { it.dependsOn(cleanOpenApi) }

        // Wire the generator task into the compilation lifecycle
//        val openApiGenerateTask = project.tasks.named("openApiGenerate")
//        project.tasks.withType<KotlinCompile>().configureEach {
//            it.dependsOn(openApiGenerateTask)
//        }
//        project.tasks.withType<JavaCompile>().configureEach {
//            it.dependsOn(openApiGenerateTask)
//        }

        project.tasks.named("openApiValidate") {
            it.enabled = ext.validateSpec.get()
            it.inputs.property("recommend", ext.recommend.get())

            it.doFirst {
                if (!ext.inputDir.get().asFile.exists()) {
                    it.logger.warn("OpenAPI schema directory does not exist: ${ext.inputDir.get()}")
                }
            }
        }

        project.tasks.named("check") {
            it.dependsOn(project.tasks.named("openApiValidate"))
        }
    }
}
