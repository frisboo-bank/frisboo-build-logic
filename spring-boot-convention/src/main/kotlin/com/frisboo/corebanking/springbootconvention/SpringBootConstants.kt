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
package com.frisboo.corebanking.springbootconvention

public object SpringBootConstants {
    public object Configuration {
        public const val ENABLE_SPRING_BOOT_BOM: String = "springBootBom.enabled"
        public const val ENABLE_JACKSON_BOM: String = "jacksonBom.enabled"

        public const val ENABLE_SPRING_BOOT_DEVTOOLS: String = "devtools.enabled"
        public const val ENABLE_SPRING_BOOT_WEB: String = "web.enabled"
        public const val ENABLE_ACTUATOR: String = "actuator.enabled"
        public const val ENABLE_SPRING_TEST: String = "test.enabled"
        public const val ENABLE_VALIDATION: String = "validation.enabled"
        public const val ENABLE_WEBFLUX: String = "webflux.enabled"
    }
}
