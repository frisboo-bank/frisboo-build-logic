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
package com.frisboo.corebanking.managers

import com.frisboo.corebanking.constants.CatalogVersionConstants
import com.frisboo.corebanking.extensions.PersistenceExtension
import com.frisboo.corebanking.utils.addImplementation
import com.frisboo.corebanking.utils.addRuntimeOnly
import com.frisboo.corebanking.utils.addTestImplementation
import com.frisboo.corebanking.utils.applyBomIfEnabled
import com.frisboo.corebanking.utils.applyCatalogPlugin
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class PersistenceManager(
    private val project: Project,
    private val ext: PersistenceExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        if (ext.enableExposed.get()) {
            project.dependencies {
                applyBomIfEnabled(ext.exposedBom)
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.EXPOSED_SPRING_BOOT_STARTER))
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.EXPOSED_JSON))
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.EXPOSED_KOTLIN_DATETIME))
            }
        }

        if (ext.enableFlyway.get()) {
            project.applyCatalogPlugin(CatalogVersionConstants.Plugins.FLYWAY)

            project.dependencies {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.FLYWAY_CORE))
                if (ext.enablePostgres.get()) {
                    addImplementation(
                        libs.libraryOrThrow(CatalogVersionConstants.Libraries.FLYWAY_DATABASE_POSTGRESQL),
                    )
                }
            }
        }

        if (ext.enableH2.get()) {
            project.dependencies {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.H2))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.TESTCONTAINERS_H2))
            }
        }

        if (ext.enableMongo.get()) {
            project.dependencies {
                addImplementation(
                    libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_DATA_MONGODB),
                )
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.MONGODB))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.TESTCONTAINERS_MONGODB))
            }
        }

        if (ext.enablePostgres.get()) {
            project.dependencies {
                addRuntimeOnly(libs.libraryOrThrow(CatalogVersionConstants.Libraries.POSTGRESQL))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.TESTCONTAINERS_POSTGRESQL))
            }
        }
    }
}
