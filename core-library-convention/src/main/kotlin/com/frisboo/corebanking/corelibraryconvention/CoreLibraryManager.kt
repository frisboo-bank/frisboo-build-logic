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
package com.frisboo.corebanking.corelibraryconvention

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.addImplementation
import com.frisboo.corebanking.coreconvention.utils.applyBomIfEnabled
import com.frisboo.corebanking.coreconvention.utils.getLibs
import com.frisboo.corebanking.coreconvention.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.dependencies

public class CoreLibraryManager(
    private val project: Project,
    private val ext: CoreLibraryExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        logger.debug("-----------------------------------------------------------")
        logger.debug("Configuring Core Library settings for project {}", project.name)
        logger.debug("Core Library BOM enabled: {}", ext.coreLibraryBom.enabled)
        logger.debug("-----------------------------------------------------------")

        project.dependencies {
            applyBomIfEnabled(ext.coreLibraryBom)

            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_CORE))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_GRPC))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_HTTP))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_MESSAGING))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_PERSISTENCE))
        }
    }
}
