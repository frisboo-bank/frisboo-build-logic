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

public object QualityConstants {
    public object Configuration {
        public const val ENABLE_CPD: String = "cpd.enabled"
        public const val ENABLE_DETEKT: String = "detekt.enabled"
        public const val ENABLE_DOKKA: String = "dokka.enabled"
        public const val ENABLE_JACOCO: String = "jacoco.enabled"
        public const val ENABLE_KOVER: String = "kover.enabled"
        public const val ENABLE_KOVER_THRESHOLDS: String = "kover.thresholds.enabled"
        public const val ENABLE_KTLINT: String = "ktlint.enabled"
        public const val ENABLE_OWASP_DEP_CHECK: String = "owasp.enabled"
        public const val ENABLE_PITEST: String = "pitest.enabled"
        public const val ENABLE_PMD: String = "pmd.enabled"
        public const val ENABLE_SPOTBUGS: String = "spotbugs.enabled"
        public const val ENABLE_SPOTLESS: String = "spotless.enabled"

        public const val OWASP_FAIL_ON_VULNERABILITIES: String = "owasp.failOnVulnerabilities"
    }
}
