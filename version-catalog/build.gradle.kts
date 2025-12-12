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
import com.vanniktech.maven.publish.VersionCatalog

plugins {
    kotlin("jvm")
    `version-catalog`
    alias(baseLibs.plugins.maven.publish)
}

description = "Version catalog for dependencies used in Core Banking modules"

catalog {
    versionCatalog {
        from(files("libs.versions.toml"))
    }
}

mavenPublishing {
    configure(VersionCatalog())
}

publishing {
    repositories {
        maven {
            name = "FrisbooGitHubPackages"
            url = uri("https://maven.pkg.github.com/jolafrite/frisboo-core-banking")
            credentials {
                username =
                    project.findProperty("frisboo.gpr.user") as String? ?: System.getenv("FRISBOO_GPR_USERNAME")
                password = project.findProperty("frisboo.gpr.key") as String? ?: System.getenv("FRISBOO_GPR_TOKEN")
            }
        }
    }
}
