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

import de.skuzzle.restrictimports.gradle.RestrictImportsExtension
import de.skuzzle.restrictimports.gradle.RestrictImportsPlugin
import org.gradle.api.Project

public class RestrictImportsManager(
    private val project: Project,
    private val ext: RestrictImportsExtension,
) {
    public fun configure() {
        project.pluginManager.apply(RestrictImportsPlugin::class.java)

        project.extensions.configure(RestrictImportsExtension::class.java) { restrictImports ->
            restrictImports.reason.set(ext.reason)
            restrictImports.bannedImports.set(ext.bannedImports)
            restrictImports.allowedImports.set(ext.allowedImports)
        }

        project.tasks.named("check").configure {
            it.dependsOn("restrictImports")
        }
    }
}
