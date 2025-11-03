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
        public const val ENABLE_QUALITY: String = "enabled"
        public const val CPD_ENABLED: String = "cpd.enabled"
        public const val DETEKT_ENABLED: String = "detekt.enabled"
        public const val DOKKA_ENABLED: String = "dokka.enabled"
        public const val JACOCO_ENABLED: String = "jacoco.enabled"
        public const val KOVER_ENABLED: String = "kover.enabled"
        public const val KOVER_THRESHOLDS_ENABLED: String = "kover.thresholds.enabled"
        public const val KTLINT_ENABLED: String = "ktlint.enabled"
        public const val OWASP_DEP_CHECK_ENABLED: String = "owasp.enabled"
        public const val OWASP_FAIL_ON_VULNERABILITIES: String = "owasp.failOnVulnerabilities"
        public const val PITEST_ENABLED: String = "pitest.enabled"
        public const val PMD_ENABLED: String = "pmd.enabled"
        public const val SPOTBUGS_ENABLED: String = "spotbugs.enabled"
        public const val SPOTLESS_ENABLED: String = "spotless.enabled"
    }
}
