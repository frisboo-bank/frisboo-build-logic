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
package com.frisboo.corebanking.coreconvention

import com.frisboo.corebanking.coreconvention.managers.BomManager
import com.frisboo.corebanking.coreconvention.managers.RestrictImportsManager
import com.frisboo.corebanking.coreconvention.managers.language.JavaLanguage
import com.frisboo.corebanking.coreconvention.managers.language.KotlinLanguage
import org.gradle.api.Project

public class CoreManager(
    private val project: Project,
    private val ext: CoreExtension,
) {
    public fun configure() {
        JavaLanguage(project)
        KotlinLanguage(project)

        RestrictImportsManager(project, ext).configure()
        BomManager(project, ext).configure()
    }
}
