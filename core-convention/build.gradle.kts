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

plugins {
    `jvm-test-suite`
    id("kotlin-conventions")
    alias(libs.plugins.dokka)
    alias(libs.plugins.plugin.publish)
}

description = "Gradle plugin that provides core convention features for to core banking apis"

dependencies {
    implementation(plugin(libs.plugins.restrictimports))
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }


gradlePlugin {
    plugins {
        create(
            "frisbooCoreBankingCoreConvention",
            Action {
                id = "com.frisboo.corebanking.core-convention"
                displayName = "Frisboo Core Banking Core Convention"
                description = "Gradle plugin that provides core convention features for to core banking apis"
                tags =
                    listOf(
                        "frisboo",
                        "frisboo-core-banking",
                        "convention-plugin",
                        "core",
                    )
                implementationClass = "com.frisboo.corebanking.coreconvention.CoreConvention"
            },
        )
    }
}

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}

val catalogsExtension = extensions.getByType<VersionCatalogsExtension>()
val libExtension = catalogsExtension.named("libs")

tasks.register<GenerateCatalogVersionConstants>("generateCatalogVersionConstants") {
    group = "Version Catalog"
    description = "Generates constants for the version catalog"
    namespace = "com.frisboo.corebanking.coreconvention"
    className = "CatalogVersionConstants"
    outputDir = layout.projectDirectory.dir("./src/main/kotlin/com/frisboo/corebanking/coreconvention")

    versionAliases.set(libExtension.versionAliases)
    pluginAliases.set(libExtension.pluginAliases)
    libraryAliases.set(libExtension.libraryAliases)
    bundleAliases.set(libExtension.bundleAliases)
}
