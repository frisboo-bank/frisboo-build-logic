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
rootProject.name = "gradle-convention-plugin"

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("version-catalog")
include("convention-plugin")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        gradlePluginPortal()
        if (providers.gradleProperty("frisboo.gpr.user").isPresent || System.getenv("FRISBOO_GPR_USERNAME") != null) {
            maven {
                name = "FrisbooGitHubPackages"
                url = uri("https://maven.pkg.github.com/jolafrite/frisboo-core-banking")
                credentials {
                    username =
                        providers.gradleProperty("frisboo.gpr.user").orNull ?: System.getenv("FRISBOO_GPR_USERNAME")
                    password = providers.gradleProperty("frisboo.gpr.key").orNull ?: System.getenv("FRISBOO_GPR_TOKEN")
                }
            }
        }
        mavenLocal()
    }

    versionCatalogs {
        create("baseLibs") {
            from(files("./version-catalog/libs.versions.toml"))
        }
    }
}
