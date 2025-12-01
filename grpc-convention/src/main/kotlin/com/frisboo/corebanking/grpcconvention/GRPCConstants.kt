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
package com.frisboo.corebanking.grpcconvention

/**
 * A utility object that contains constants related to gRPC configuration.
 */
public object GRPCConstants {

    /**
     * Contains configuration keys for enabling/disabling various gRPC-related features.
     */
    public object Configuration {
        /**
         * Configuration key to enable or disable Kotlin gRPC stubs.
         * Value: "kotlinStub.enabled"
         */
        public const val ENABLE_GRPC_KOTLIN_STUB: String = "kotlinStub.enabled"

        /**
         * Configuration key to enable or disable gRPC Netty.
         * Value: "netty.enabled"
         */
        public const val ENABLE_GRPC_NETTY: String = "netty.enabled"

        /**
         * Configuration key to enable or disable gRPC Protobuf.
         * Value: "protobuf.enabled"
         */
        public const val ENABLE_GRPC_PROTOBUF: String = "protobuf.enabled"

        /**
         * Configuration key to enable or disable Protobuf Kotlin.
         * Value: "protobufKotlin.enabled"
         */
        public const val ENABLE_PROTOBUF_KOTLIN: String = "protobufKotlin.enabled"

        /**
         * Configuration key to enable or disable the Protobuf plugin.
         * Value: "protobufPlugin.enabled"
         */
        public const val ENABLE_PROTOBUF_PLUGIN: String = "protobufPlugin.enabled"

        /**
         * Configuration key to enable or disable the gRPC BOM.
         * Value: "grpcBom.enabled"
         */
        public const val ENABLE_GRPC_BOM: String = "grpcBom.enabled"

        /**
         * Configuration key to enable or disable the Spring Boot gRPC BOM.
         * Value: "springBootGrpcBom.enabled"
         */
        public const val ENABLE_SPRING_BOOT_GRPC_BOM: String = "springBootGrpcBom.enabled"

        /**
         * Configuration key to enable or disable the Spring Boot gRPC Starter.
         * Value: "springBootGrpcStarter.enabled"
         */
        public const val ENABLE_SPRING_BOOT_GRPC_STARTER: String = "springBootGrpcStarter.enabled"
    }
}
