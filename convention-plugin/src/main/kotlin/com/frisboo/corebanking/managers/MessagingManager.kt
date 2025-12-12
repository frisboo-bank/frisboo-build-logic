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
import com.frisboo.corebanking.extensions.MessagingExtension
import com.frisboo.corebanking.utils.addImplementation
import com.frisboo.corebanking.utils.addTestImplementation
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.dependencies

public class MessagingManager(
    private val project: Project,
    private val ext: MessagingExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        logger.debug("-----------------------------------------------------------")
        logger.debug("Configuring Messaging settings for project ${project.name}")
        logger.debug(" - Kafka Version: ${ext.enableKafka.get()}")
        logger.debug("-----------------------------------------------------------")

        if (ext.enableKafka.get()) {
            configureKafka()
        }
    }

    private fun configureKafka() {
        project.dependencies {
            addImplementation(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_KAFKA),
            )
            addTestImplementation(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_KAFKA_TEST),
            )
            addTestImplementation(
                libs.libraryOrThrow(CatalogVersionConstants.Libraries.TESTCONTAINERS_KAFKA),
            )
        }
    }
}
