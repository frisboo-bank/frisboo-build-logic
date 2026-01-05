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
package com.frisboo.corebanking.constants

public object ConfigurationConstants {
    /**
     * General plugin configuration keys.
     */
    public object Plugin {
        public const val ENABLE_CACHING: String = "caching.enabled"
        public const val ENABLE_GRPC: String = "grpc.enabled"
        public const val ENABLE_KOTLIN: String = "kotlin.enabled"
        public const val ENABLE_MESSAGING: String = "messaging.enabled"
        public const val ENABLE_OPENAPI: String = "openapi.enabled"
        public const val ENABLE_PERSISTENCE: String = "persistence.enabled"
        public const val ENABLE_QUALITY: String = "quality.enabled"
        public const val ENABLE_SPRING_BOOT: String = "spring-boot.enabled"
        public const val ENABLE_TELEMETRY: String = "telemetry.enabled"
        public const val ENABLE_TESTING: String = "testing.enabled"
    }

    /**
     * Configuration keys for Core Library.
     */
    public object CoreLibrary {
        private const val CORE_LIBRARY_PREFIX = "coreLibrary"
        public const val ENABLE_COREBANKING_LIBRARY_BOM: String = "$CORE_LIBRARY_PREFIX.coreLibraryBom.enabled"
    }

    /**
     * Configuration keys for gRPC.
     */
    public object GRPC {
        private const val GRPC_PREFIX = "grpc"
        public const val ENABLE_GRPC_KOTLIN_STUB: String = "$GRPC_PREFIX.kotlinStub.enabled"
        public const val ENABLE_GRPC_NETTY: String = "$GRPC_PREFIX.netty.enabled"
        public const val ENABLE_GRPC_PROTOBUF: String = "$GRPC_PREFIX.protobuf.enabled"
        public const val ENABLE_PROTOBUF_KOTLIN: String = "$GRPC_PREFIX.protobufKotlin.enabled"
        public const val ENABLE_PROTOBUF_PLUGIN: String = "$GRPC_PREFIX.protobufPlugin.enabled"
        public const val ENABLE_GRPC_BOM: String = "$GRPC_PREFIX.grpcBom.enabled"
        public const val ENABLE_SPRING_BOOT_GRPC_BOM: String = "$GRPC_PREFIX.springBootGrpcBom.enabled"
        public const val ENABLE_SPRING_BOOT_GRPC_STARTER: String = "$GRPC_PREFIX.springBootGrpcStarter.enabled"
    }

    /**
     * Configuration keys for Kotlin.
     */
    public object Kotlin {
        private const val KOTLIN_PREFIX = "kotlin"
        public const val ENABLE_ARROW_KT_BOM: String = "$KOTLIN_PREFIX.arrowKtBom.enabled"
        public const val ENABLE_KOTLIN_BOM: String = "$KOTLIN_PREFIX.kotlinBom.enabled"
        public const val ENABLE_KOTLINX_COROUTINES_BOM: String = "$KOTLIN_PREFIX.kotlinxCoroutinesBom.enabled"
        public const val ENABLE_KOTLINX_SERIALIZATION_BOM: String = "$KOTLIN_PREFIX.kotlinxSerializationBom.enabled"
        public const val ENABLE_REACTOR_BOM: String = "$KOTLIN_PREFIX.reactorBom.enabled"
        public const val ENABLE_ARROW_KT: String = "$KOTLIN_PREFIX.arrow.enabled"
        public const val ENABLE_COROUTINES: String = "$KOTLIN_PREFIX.coroutines.enabled"
        public const val ENABLE_COROUTINES_REACTOR: String = "$KOTLIN_PREFIX.coroutinesReactor.enabled"
        public const val ENABLE_JETBRAINS_ANNOTATIONS: String = "$KOTLIN_PREFIX.jetbrainsAnnotations.enabled"
        public const val ENABLE_KOTLIN_REFLECT: String = "$KOTLIN_PREFIX.reflect.enabled"
        public const val ENABLE_REACTOR_KOTLIN_EXTENSIONS: String = "$KOTLIN_PREFIX.kotlinExtensions.enabled"
    }

    public object Caching {
        private const val CACHING_PREFIX = "caching"
        public const val ENABLE_REDIS: String = "$CACHING_PREFIX.redis.enabled"
    }

    /**
     * Configuration keys for Messaging.
     */
    public object Messaging {
        private const val MESSAGING_PREFIX = "messaging"
        public const val ENABLE_KAFKA: String = "$MESSAGING_PREFIX.kafka.enabled"
    }

    /**
     * Configuration keys for OpenAPI.
     */
    public object Openapi {
        private const val OPENAPI_PREFIX = "openapi"
        public const val ENABLE_SPRINGDOC_OPENAPI_BOM: String = "$OPENAPI_PREFIX.springdocOpenapiBom.enabled"
        public const val ENABLE_SPRINGDOC_OPENAPI_WEBFLUX: String = "$OPENAPI_PREFIX.springdocOpenapiWebflux.enabled"
    }

    /**
     * Configuration keys for Persistence.
     */
    public object Persistence {
        private const val PERSISTENCE_PREFIX = "persistence"
        public const val ENABLE_EXPOSED: String = "$PERSISTENCE_PREFIX.exposed.enabled"
        public const val ENABLE_EXPOSED_BOM: String = "$PERSISTENCE_PREFIX.exposedBom.enabled"
        public const val ENABLE_FLYWAY: String = "$PERSISTENCE_PREFIX.flyway.enabled"
        public const val ENABLE_H2: String = "$PERSISTENCE_PREFIX.h2.enabled"
        public const val ENABLE_MONGODB: String = "$PERSISTENCE_PREFIX.mongodb.enabled"
        public const val ENABLE_POSTGRESQL: String = "$PERSISTENCE_PREFIX.postgresql.enabled"
    }

    /**
     * Configuration keys for Quality tools.
     */
    public object Quality {
        private const val QUALITY_PREFIX = "quality"
        public const val ENABLE_CPD: String = "$QUALITY_PREFIX.cpd.enabled"
        public const val ENABLE_DETEKT: String = "$QUALITY_PREFIX.detekt.enabled"
        public const val ENABLE_DOKKA: String = "$QUALITY_PREFIX.dokka.enabled"
        public const val ENABLE_JACOCO: String = "$QUALITY_PREFIX.jacoco.enabled"
        public const val ENABLE_KOVER: String = "$QUALITY_PREFIX.kover.enabled"
        public const val ENABLE_KOVER_THRESHOLDS: String = "$QUALITY_PREFIX.kover.thresholds.enabled"
        public const val ENABLE_KTLINT: String = "$QUALITY_PREFIX.ktlint.enabled"
        public const val ENABLE_OWASP_DEP_CHECK: String = "$QUALITY_PREFIX.owasp.enabled"
        public const val ENABLE_PITEST: String = "$QUALITY_PREFIX.pitest.enabled"
        public const val ENABLE_PMD: String = "$QUALITY_PREFIX.pmd.enabled"
        public const val ENABLE_SPOTBUGS: String = "$QUALITY_PREFIX.spotbugs.enabled"
        public const val ENABLE_SPOTLESS: String = "$QUALITY_PREFIX.spotless.enabled"
        public const val OWASP_FAIL_ON_VULNERABILITIES: String = "$QUALITY_PREFIX.owasp.failOnVulnerabilities"
    }

    /**
     * Configuration keys for Spring Boot.
     */
    public object SpringBoot {
        private const val SPRING_BOOT_PREFIX = "springBoot"
        public const val ENABLE_SPRING_BOOT_BOM: String = "$SPRING_BOOT_PREFIX.springBootBom.enabled"
        public const val ENABLE_JACKSON_BOM: String = "$SPRING_BOOT_PREFIX.jacksonBom.enabled"
        public const val ENABLE_SPRING_BOOT_DEVTOOLS: String = "$SPRING_BOOT_PREFIX.devtools.enabled"
        public const val ENABLE_SPRING_BOOT_WEB: String = "$SPRING_BOOT_PREFIX.web.enabled"
        public const val ENABLE_ACTUATOR: String = "$SPRING_BOOT_PREFIX.actuator.enabled"
        public const val ENABLE_SPRING_TEST: String = "$SPRING_BOOT_PREFIX.test.enabled"
        public const val ENABLE_VALIDATION: String = "$SPRING_BOOT_PREFIX.validation.enabled"
        public const val ENABLE_WEBFLUX: String = "$SPRING_BOOT_PREFIX.webflux.enabled"
    }

    /**
     * Configuration keys for Telemetry.
     */
    public object Telemetry {
        private const val TELEMETRY_PREFIX = "telemetry"
        public const val ENABLE_OPEN_TELEMETRY_EXPORTER_OTLP: String =
            "$TELEMETRY_PREFIX.opentelemetryExporterOtlp.enabled"
    }

    /**
     * Configuration keys for Testing.
     */
    public object Testing {
        private const val TESTING_PREFIX = "testing"
        public const val ENABLE_TESTCONTAINERS_BOM: String = "$TESTING_PREFIX.testcontainersBom.enabled"
        public const val ENABLE_JUNIT_BOM: String = "$TESTING_PREFIX.junitBom.enabled"
        public const val ENABLE_KOTEST_BOM: String = "$TESTING_PREFIX.kotestBom.enabled"
        public const val ENABLE_TESTCONTAINERS: String = "$TESTING_PREFIX.testcontainers.enabled"
    }
}
