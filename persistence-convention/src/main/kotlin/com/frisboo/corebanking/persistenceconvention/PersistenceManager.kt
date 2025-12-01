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
package com.frisboo.corebanking.persistenceconvention

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.addImplementation
import com.frisboo.corebanking.coreconvention.utils.addRuntimeOnly
import com.frisboo.corebanking.coreconvention.utils.addTestImplementation
import com.frisboo.corebanking.coreconvention.utils.applyBomIfEnabled
import com.frisboo.corebanking.coreconvention.utils.getLibs
import com.frisboo.corebanking.coreconvention.utils.libraryOrThrow
import com.frisboo.corebanking.coreconvention.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.dependencies

public class PersistenceManager(
    private val project: Project,
    private val ext: PersistenceExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        logger.debug("-----------------------------------------------------------")
        logger.debug("Configuring Persistence settings for project ${project.name}")
        logger.debug(" - Exposed Enabled: ${ext.enableExposed.get()}")
        logger.debug(" - Exposed BOM Enabled: ${ext.exposedBom.enabled.get()}")
        logger.debug(" - Flyway Enabled: ${ext.enableFlyway.get()}")
        logger.debug(" - H2 Enabled: ${ext.enableH2.get()}")
        logger.debug(" - Mongo Enabled: ${ext.enableMongo.get()}")
        logger.debug(" - Postgres Enabled: ${ext.enablePostgres.get()}")
        logger.debug("-----------------------------------------------------------")

        project.dependencies {
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_DATA_JPA))
        }

        if (ext.enableExposed.get()) {
            project.dependencies {
                applyBomIfEnabled(ext.exposedBom)
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.EXPOSED_SPRING_BOOT_STARTER))
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.EXPOSED_JSON))
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.EXPOSED_KOTLIN_DATETIME))
            }
        }

        if (ext.enableFlyway.get()) {
            project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.FLYWAY))
            project.dependencies {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.FLYWAY_CORE))
                if (ext.enableMongo.get()) {
//                    addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.FLYWAY_DATABASE_MONGODB))
                }
                if (ext.enablePostgres.get()) {
                    addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.FLYWAY_DATABASE_POSTGRESQL))
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
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_DATA_MONGODB))
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
