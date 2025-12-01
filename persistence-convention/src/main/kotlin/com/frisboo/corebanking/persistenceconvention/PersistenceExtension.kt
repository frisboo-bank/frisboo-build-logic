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
package com.frisboo.corebanking.persistenceconvention

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.BomExtensionSpec
import com.frisboo.corebanking.coreconvention.utils.gradleProperty
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class PersistenceExtension
@Inject constructor(
    objects: ObjectFactory,
    providers: ProviderFactory,
    libs: VersionCatalog,
) {
    public val enableExposed: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            PersistenceConstants.Configuration.ENABLE_EXPOSED,
            String::toBoolean,
        ).orElse(false),
    )

    public val exposedBom: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        CatalogVersionConstants.Libraries.EXPOSED_BOM,
        PersistenceConstants.Configuration.ENABLE_EXPOSED_BOM,
        false,
    )

    public val enableFlyway: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            PersistenceConstants.Configuration.ENABLE_FLYWAY,
            String::toBoolean,
        ).orElse(false),
    )

    public val enableH2: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            PersistenceConstants.Configuration.ENABLE_H2,
            String::toBoolean,
        ).orElse(false),
    )

    public val enableMongo: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            PersistenceConstants.Configuration.ENABLE_MONGODB,
            String::toBoolean,
        ).orElse(false),
    )

    public val enablePostgres: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            PersistenceConstants.Configuration.ENABLE_POSTGRESQL,
            String::toBoolean,
        ).orElse(false),
    )
}
