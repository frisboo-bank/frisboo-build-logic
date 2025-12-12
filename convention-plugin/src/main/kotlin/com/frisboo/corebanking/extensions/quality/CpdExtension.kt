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
package com.frisboo.corebanking.extensions.quality

import com.frisboo.corebanking.constants.ConfigurationConstants
import com.frisboo.corebanking.extensions.QualityExtension
import com.frisboo.corebanking.utils.gradleProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class CpdExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
    ) {
        public val enabled: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers.gradleProperty(ConfigurationConstants.Quality.ENABLE_CPD, String::toBoolean).orElse(true),
            )

        public val failOnViolation: Property<Boolean> = objects.property<Boolean>().convention(true)

        public val source: Property<FileCollection> =
            objects.property<FileCollection>().convention(objects.fileCollection())

        public val minimumTokenCount: Property<Int> =
            objects.property<Int>().convention(QualityExtension.DEFAULT_TOKEN_COUNT)

        public fun enabled(value: Boolean): Unit = enabled.set(value)

        public fun failOnViolation(value: Boolean): Unit = failOnViolation.set(value)

        public fun source(path: FileCollection): Unit = source.set(path)

        public fun minimumTokenCount(value: Int): Unit = minimumTokenCount.set(value)
    }
