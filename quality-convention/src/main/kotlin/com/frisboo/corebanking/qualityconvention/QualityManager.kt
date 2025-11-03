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
package com.frisboo.corebanking.qualityconvention

import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.qualityconvention.managers.configureCpd
import com.frisboo.corebanking.qualityconvention.managers.configureDetekt
import org.gradle.api.Project

public class QualityManager(
    private val project: Project,
    private val ext: QualityExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        if (!ext.enabled.get()) return

        project.configureCpd(ext, libs)
        project.configureDetekt(ext, libs)
    }
}
