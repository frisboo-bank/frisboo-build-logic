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
package com.frisboo.corebanking.convention

import com.frisboo.corebanking.coreconvention.utils.getVersionOrFail
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class PluginExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
        layout: ProjectLayout,
        libs: VersionCatalog,
    ) {
        private val projectName = layout.projectDirectory.asFile.name

        public val frisbooCoreBankingVersion: Property<String> =
            objects.property<String>().convention(
                providers
                    .gradleProperty("frisboo-corebanking-version")
                    .orElse(libs.getVersionOrFail(PluginConstants.Plugin.FRISBOO_COREBANKING_VERSION)),
            )

        public val artifactId: Property<String> =
            objects.property<String>().convention(
                projectName.removePrefix("frisboo-corebanking-").removeSuffix("-plugin"),
            )

        public val displayName: Property<String> =
            objects.property<String>().convention(
                artifactId.map { id ->
                    id.split("-").joinToString(" ") { part -> part.replaceFirstChar { it.titlecase() } }
                },
            )
    }
