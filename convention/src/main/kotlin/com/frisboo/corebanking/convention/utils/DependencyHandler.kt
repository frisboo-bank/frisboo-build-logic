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
package com.frisboo.corebanking.convention.utils

import com.frisboo.corebanking.convention.extensions.boms.BomExtensionSpec
import org.gradle.api.artifacts.dsl.DependencyHandler

public fun DependencyHandler.applyBomIfEnabled(bom: BomExtensionSpec) {
    val enabled = bom.enabled.get()
    val coordinates = bom.coordinates.get()
    val testOnly = bom.testOnly.get()

    if (!enabled) return

    val dep = platform(coordinates)

    if (!testOnly) {
        add("implementation", dep)
    }

    add("testImplementation", dep)
}
