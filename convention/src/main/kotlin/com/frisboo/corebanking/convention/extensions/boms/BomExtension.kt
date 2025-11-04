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
package com.frisboo.corebanking.convention.extensions.boms

import com.frisboo.corebanking.convention.ConfigurationConstants
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

public open class BomExtension
@Inject constructor(
    objects: ObjectFactory,
    libs: VersionCatalog,
) {
    public val customBoms: NamedDomainObjectContainer<CustomBomExtensionSpec> =
        objects.domainObjectContainer(CustomBomExtensionSpec::class.java)


    public val coreBanking: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        "frisboo-corebanking-bom",
        ConfigurationConstants.Bom.CORE_BANKING,
        false,
    )
    public val exposed: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        "exposed-bom",
        ConfigurationConstants.Bom.EXPOSED,
        false,
    )
    public val jackson: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        "jackson-bom",
        ConfigurationConstants.Bom.JACKSON,
        false,
    )
    public val junit: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        "junit-bom",
        ConfigurationConstants.Bom.JUNIT,
        true,
    )
    public val testcontainers: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        "testcontainers-bom",
        ConfigurationConstants.Bom.TESTCONTAINERS,
        true,
    )

    public fun customBoms(action: Action<NamedDomainObjectContainer<CustomBomExtensionSpec>>) {
        action.execute(customBoms)
    }
    public fun exposed(action: Action<BomExtensionSpec>) {
        action.execute(exposed)
    }
    public fun jackson(action: Action<BomExtensionSpec>) {
        action.execute(jackson)
    }
    public fun junit(action: Action<BomExtensionSpec>) {
        action.execute(junit)
    }
    public fun testcontainers(action: Action<BomExtensionSpec>) {
        action.execute(testcontainers)
    }
}
