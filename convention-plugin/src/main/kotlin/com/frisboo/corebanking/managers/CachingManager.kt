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
import com.frisboo.corebanking.extensions.CachingExtension
import com.frisboo.corebanking.utils.addImplementation
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class CachingManager(
    private val project: Project,
    private val ext: CachingExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        if (ext.enableRedis.get()) {
            configureRedis()
        }
    }

    private fun configureRedis() {
        project.dependencies {
            addImplementation(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_DATA_REDIS),
            )
            addImplementation(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_STARTER_DATA_REDIS_REACTIVE),
            )
        }
    }
}
