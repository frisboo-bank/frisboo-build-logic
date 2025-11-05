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
package com.frisboo.corebanking.qualityconvention.extensions

import com.frisboo.corebanking.convention.utils.gradleProperty
import com.frisboo.corebanking.qualityconvention.QualityConstants
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class PitestExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
    ) {
        public val enabled: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        QualityConstants.Configuration.ENABLE_PITEST,
                        String::toBoolean,
                    ).orElse(true),
            )
    }
