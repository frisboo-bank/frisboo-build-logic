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

public object GRPCConstants {
    public object Configuration {
        public const val ENABLE_GRPC_KOTLIN_STUB: String = "kotlinStub.enabled"
        public const val ENABLE_GRPC_NETTY: String = "netty.enabled"
        public const val ENABLE_GRPC_PROTOBUF: String = "protobuf.enabled"
        public const val ENABLE_PROTOBUF_KOTLIN: String = "protobufKotlin.enabled"
        public const val ENABLE_PROTOBUF_PLUGIN: String = "protobufPlugin.enabled"
    }
}
