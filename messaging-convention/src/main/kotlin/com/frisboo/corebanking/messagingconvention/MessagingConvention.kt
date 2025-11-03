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
package com.frisboo.corebanking.messagingconvention

import com.frisboo.corebanking.convention.utils.getLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.repositories

public class MessagingConvention : Plugin<Project> {
    override fun apply(target: Project): Unit =
        with(target) {
            repositories {
                mavenCentral()
                gradlePluginPortal()
                mavenLocal()
            }

            val libs = getLibs()
            val ext = extensions.create<MessagingExtension>("coreBankingMessaging", libs)

            project.afterEvaluate {
                try {
                    MessagingManager(this, ext).configure()
                } catch (e: IllegalStateException) {
                    error("Failed to configure Frisboo Core Banking Messaging Convention: ${e.message}")
                }
            }
        }
}
