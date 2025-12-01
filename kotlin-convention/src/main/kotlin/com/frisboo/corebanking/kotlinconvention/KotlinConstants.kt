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

public object KotlinConstants {
    public object Configuration {
        public const val ENABLE_ARROW_KT_BOM: String = "arrowKtBom.enabled"
        public const val ENABLE_KOTLIN_BOM: String = "kotlinBom.enabled"
        public const val ENABLE_KOTLINX_COROUTINES_BOM: String = "kotlinxCoroutinesBom.enabled"
        public const val ENABLE_KOTLINX_SERIALIZATION_BOM: String = "kotlinxSerializationBom.enabled"
        public const val ENABLE_REACTOR_BOM: String = "reactorBom.enabled"

        public const val ENABLE_ARROW_KT: String = "arrow.enabled"
        public const val ENABLE_COROUTINES: String = "coroutines.enabled"
        public const val ENABLE_COROUTINES_REACTOR: String = "coroutinesReactor.enabled"
        public const val ENABLE_JETBRAINS_ANNOTATIONS: String = "jetbrainsAnnotations.enabled"
        public const val ENABLE_KOTLIN_REFLECT: String = "reflect.enabled"
        public const val ENABLE_REACTOR_KOTLIN_EXTENSIONS: String = "kotlinExtensions.enabled"
    }
}
