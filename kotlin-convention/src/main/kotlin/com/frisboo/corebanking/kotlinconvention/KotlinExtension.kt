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
package com.frisboo.corebanking.kotlinconvention

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.BomExtensionSpec
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ProviderFactory
import javax.inject.Inject

public open class KotlinExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
        libs: VersionCatalog,
    ) {
        public val arrowKtBom: BomExtensionSpec =
            objects.newInstance(
                BomExtensionSpec::class.java,
                libs,
                CatalogVersionConstants.Libraries.ARROW_KT_BOM,
                KotlinConstants.Configuration.ENABLE_ARROW_KT_BOM,
                false,
            )

        public val kotlinBom: BomExtensionSpec =
            objects.newInstance(
                BomExtensionSpec::class.java,
                libs,
                CatalogVersionConstants.Libraries.KOTLIN_BOM,
                KotlinConstants.Configuration.ENABLE_KOTLIN_BOM,
                false,
            )

        public val kotlinxCoroutinesBom: BomExtensionSpec =
            objects.newInstance(
                BomExtensionSpec::class.java,
                libs,
                CatalogVersionConstants.Libraries.KOTLINX_COROUTINES_BOM,
                KotlinConstants.Configuration.ENABLE_KOTLINX_COROUTINES_BOM,
                false,
            )

        public val reactiveBom: BomExtensionSpec =
            objects.newInstance(
                BomExtensionSpec::class.java,
                libs,
                CatalogVersionConstants.Libraries.REACTOR_BOM,
                KotlinConstants.Configuration.ENABLE_REACTOR_BOM,
                false,
            )
    }
