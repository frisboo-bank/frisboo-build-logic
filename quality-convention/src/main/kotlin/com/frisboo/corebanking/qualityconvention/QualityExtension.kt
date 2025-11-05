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

import com.frisboo.corebanking.qualityconvention.extensions.CpdExtension
import com.frisboo.corebanking.qualityconvention.extensions.DetektExtension
import com.frisboo.corebanking.qualityconvention.extensions.DokkaExtension
import com.frisboo.corebanking.qualityconvention.extensions.JacocoExtension
import com.frisboo.corebanking.qualityconvention.extensions.KoverExtension
import com.frisboo.corebanking.qualityconvention.extensions.OwaspDepCheckExtension
import com.frisboo.corebanking.qualityconvention.extensions.PitestExtension
import com.frisboo.corebanking.qualityconvention.extensions.PmdExtension
import com.frisboo.corebanking.qualityconvention.extensions.SpotbugsExtension
import com.frisboo.corebanking.qualityconvention.extensions.SpotlessExtension
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.newInstance
import javax.inject.Inject

public open class QualityExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
    ) {
        public val cpd: CpdExtension = objects.newInstance()
        public val detekt: DetektExtension = objects.newInstance()
        public val dokka: DokkaExtension = objects.newInstance()
        public val jacoco: JacocoExtension = objects.newInstance()
        public val kover: KoverExtension = objects.newInstance()
        public val owaspDependencyCheck: OwaspDepCheckExtension = objects.newInstance()
        public val pitest: PitestExtension = objects.newInstance()
        public val pmd: PmdExtension = objects.newInstance()
        public val spotbugs: SpotbugsExtension = objects.newInstance()
        public val spotless: SpotlessExtension = objects.newInstance()

        public companion object {
            public const val DEFAULT_CODE_COVERAGE_THRESHOLD: Double = 0.8
            public const val DEFAULT_KOVER_THRESHOLD: Int = 80
            public const val DEFAULT_MUTATION_THRESHOLD: Int = 85
            public const val DEFAULT_OWASP_THRESHOLD: Float = 7.0f
            public const val DEFAULT_THREADS: Int = 4
            public const val DEFAULT_TOKEN_COUNT: Int = 50
        }
    }
