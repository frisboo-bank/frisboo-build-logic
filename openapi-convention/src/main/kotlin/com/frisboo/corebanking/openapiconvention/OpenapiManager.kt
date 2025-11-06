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

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.addImplementation
import com.frisboo.corebanking.coreconvention.utils.applyBomIfEnabled
import com.frisboo.corebanking.coreconvention.utils.copyWithRecursion
import com.frisboo.corebanking.coreconvention.utils.getLibs
import com.frisboo.corebanking.coreconvention.utils.libraryOrThrow
import com.frisboo.corebanking.coreconvention.utils.onSpringBootEnabled
import com.frisboo.corebanking.coreconvention.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorValidateExtension

public class OpenapiManager(
    private val project: Project,
    private val ext: OpenapiExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        logger.debug("-----------------------------------")
        logger.debug("Configuring OpenAPI settings for project: ${project.name}")
        logger.debug(" - Input Dir: ${ext.inputDir.get()}")
        logger.debug(" - Schema Filename: ${ext.schemaFilename.get()}")
        logger.debug(" - Output Dir: ${ext.outputDir.get()}")
        logger.debug(" - Package Name: ${ext.packageName.get()}")
        logger.debug(" - Generate APIs: ${ext.generateApis.get()}")
        logger.debug(" - Generate Models: ${ext.generateModels.get()}")
        logger.debug(" - Generate API Tests: ${ext.generateApiTests.get()}")
        logger.debug(" - Generate Model Tests: ${ext.generateModelTests.get()}")
        logger.debug(" - Validate Spec: ${ext.validateSpec.get()}")
        logger.debug("-----------------------------------")

        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.OPENAPI_GENERATOR))

        project.onSpringBootEnabled {
            configureSpringBoot()
        }
    }

    private fun configureSpringBoot() {
        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.SPRINGDOC_OPENAPI))

        project.dependencies {
            applyBomIfEnabled(ext.springdocOpenapiBom)
            addImplementation(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRINGDOC_OPENAPI_STARTER_WEBFLUX_UI),
            )
            addImplementation(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRINGDOC_OPENAPI_STARTER_WEBFLUX_API),
            )
        }

        val generatedInputSpec =
            ext.inputDir.zip(ext.schemaFilename) { dir, filename -> dir.file(filename).asFile.absolutePath }

        val userTemplateDir =
            project.layout.buildDirectory
                .dir("openapi-templates")
                .get()

        project.extensions.configure<OpenApiGeneratorGenerateExtension> {
            generatorName.set("kotlin-spring")
            inputSpec.set(generatedInputSpec)
            outputDir.set(
                ext.outputDir
                    .get()
                    .asFile.absolutePath,
            )
            packageName.set(ext.packageName)
            id.set("openapi-documentation")
            templateDir.set(userTemplateDir.dir("kotlin-spring").asFile.absolutePath)
            modelNameSuffix.set("Dto")

            verbose.set(false)
            logToStderr.set(true)

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
                    "useTags" to "true",
                    "library" to "spring-boot",
                    "reactive" to "true",
                    "delegatePattern" to "true",
                    "useCoroutines" to "true",
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

        // Create a task to copy templates FIRST
        project.tasks.register<Task>("copyOpenapiTemplates") {
            group = "openapi tools"
            description = "Copies OpenAPI templates to build directory"

            val templatesUrl =
                OpenapiManager::class.java.classLoader.getResource("openapi-templates")
                    ?: error("Failed to locate OpenAPI templates for kotlin-spring generator")

            inputs.property("templatesUrl", templatesUrl.toString())
            outputs.dir(userTemplateDir)

            doFirst {
                userTemplateDir.asFile.mkdirs()
                templatesUrl.copyWithRecursion(userTemplateDir)

                val templateFiles =
                    userTemplateDir.asFile
                        .walk()
                        .filter { it.isFile }
                        .toList()
                logger.debug("Copied ${templateFiles.size} template files to: ${userTemplateDir.asFile.absolutePath}")
            }
        }

        // Clean task for generated sources
        val cleanOpenApi =
            project.tasks.register<Delete>("cleanOpenApi") {
                group = "openapi tools"
                description = "Cleans generated OpenAPI sources"
                delete(ext.outputDir)
                delete(userTemplateDir)
            }
        project.tasks.named("clean").configure { it.dependsOn(cleanOpenApi) }

        val openApiGenerateTask = project.tasks.named("openApiGenerate")
        openApiGenerateTask.configure {
            it.dependsOn("copyOpenapiTemplates")
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            it.dependsOn(openApiGenerateTask)
        }
        project.tasks.withType<JavaCompile>().configureEach {
            it.dependsOn(openApiGenerateTask)
        }

        project.tasks.named("openApiValidate") {
            it.enabled = ext.validateSpec.get()
            it.inputs.property("recommend", ext.recommend.get())

            it.doFirst {
                if (!ext.inputDir
                        .get()
                        .asFile
                        .exists()
                ) {
                    it.logger.warn("OpenAPI schema directory does not exist: ${ext.inputDir.get()}")
                }
            }
        }

        project.tasks.named("check") {
            it.dependsOn(project.tasks.named("openApiValidate"))
        }

        project.tasks.named("check") {
            it.dependsOn(openApiGenerateTask)
        }
    }
}
