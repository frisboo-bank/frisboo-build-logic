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
package com.frisboo.corebanking.convention.extensions

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.listProperty
import javax.inject.Inject

public open class TestingExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
        layout: ProjectLayout,
        libs: VersionCatalog,
    ) {
        public val testJvmArguments: ListProperty<String> =
            objects.listProperty<String>().convention(
//                gradleProperty(providers, Configurations.Plugin.TEST_JVM_ARGS) {
//                    it.split(",").map(String::trim)
//                }.orElse(
                listOf(
                    "--add-opens=java.base/java.lang=ALL-UNNAMED",
                    "--add-opens=java.base/java.io=ALL-UNNAMED",
                    "--add-opens=java.base/java.util=ALL-UNNAMED",
                ),
//                ),
            )
    }
