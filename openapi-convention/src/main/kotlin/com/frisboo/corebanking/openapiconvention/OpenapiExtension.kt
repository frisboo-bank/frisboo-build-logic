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

import com.frisboo.corebanking.coreconvention.utils.BomExtensionSpec
import com.frisboo.corebanking.coreconvention.utils.gradleProperty
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class OpenapiExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
        libs: VersionCatalog,
        layout: ProjectLayout,
    ) {
        private val projectName = layout.projectDirectory.asFile.name

        public val springdocOpenapiBom: BomExtensionSpec =
            objects.newInstance(
                BomExtensionSpec::class.java,
                libs,
                "springdoc-openapi-bom",
                OpenapiConstants.Configuration.ENABLE_SPRINGDOC_OPENAPI_BOM,
                false,
            )

        public val springdocOpenapiWebflux: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(OpenapiConstants.Configuration.ENABLE_SPRINGDOC_OPENAPI_WEBFLUX, String::toBoolean)
                    .orElse(true),
            )

        // --- Paths and Naming ---
        public val outputDir: DirectoryProperty =
            objects.directoryProperty().convention(
                layout.buildDirectory.dir("generated/sources/openapi"),
            )
        public val packageName: Property<String> =
            objects.property(String::class.java).convention(
                "com.frisboo.corebanking.${projectName.replace("-", "")}",
            )
        public val inputDir: DirectoryProperty =
            objects.directoryProperty().convention(
                layout.projectDirectory.dir("src/api/schemas"),
            )
        public val schemaFilename: Property<String> = objects.property<String>().convention("$projectName-api.yaml")

        // --- Build Information ---
        public val groupId: Property<String> = objects.property(String::class.java)
        public val artifactId: Property<String> = objects.property(String::class.java)
        public val artifactVersion: Property<String> = objects.property(String::class.java)

        // --- Generation Toggles ---
        public val generateApiDocumentation: Property<Boolean> = objects.property<Boolean>().convention(false)
        public val generateApis: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val generateApiTests: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val generateModels: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val generateModelDocumentation: Property<Boolean> = objects.property<Boolean>().convention(false)
        public val generateModelTests: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val generateSupportingFiles: Property<Boolean> = objects.property<Boolean>().convention(false)
        public val generateApiClients: Property<Boolean> = objects.property<Boolean>().convention(false)

        // --- Code Generation Options ---
        public val useJakartaEe: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val useSpringBoot3: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val useReactive: Property<Boolean> = objects.property<Boolean>().convention(false)
        public val useSwaggerAnnotations: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val useBeanValidation: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val useOptional: Property<Boolean> = objects.property<Boolean>().convention(false)
        public val useTags: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val useLombok: Property<Boolean> = objects.property<Boolean>().convention(true)

        // --- Validation ---
        public val validateSpec: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val recommend: Property<Boolean> = objects.property<Boolean>().convention(true)
        public val skipIfSpecIsUnchanged: Property<Boolean> = objects.property<Boolean>().convention(true)

        // --- Additional Configuration ---
        public val dateLibrary: Property<String> = objects.property<String>().convention("java8")
        public val serializationLibrary: Property<String> = objects.property<String>().convention("jackson")
        public val configOptions: MapProperty<String, String> =
            objects.mapProperty(String::class.java, String::class.java).convention(emptyMap<String, String>())
    }
