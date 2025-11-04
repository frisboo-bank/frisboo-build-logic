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
package com.frisboo.corebanking.convention.managers

import com.frisboo.corebanking.convention.CatalogVersionConstants
import com.frisboo.corebanking.convention.extensions.CoreBankingExtension
import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class CoreBankingManager(
    private val project: Project,
    private val ext: CoreBankingExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        if (!ext.enabled.get()) return

        project.dependencies {
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_CORE))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_HTTP))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_SPRING_BOOT))
            add("implementation", libs.libraryOrThrow(CatalogVersionConstants.Libraries.COREBANKING_TRANSACTION))
        }
    }
}
