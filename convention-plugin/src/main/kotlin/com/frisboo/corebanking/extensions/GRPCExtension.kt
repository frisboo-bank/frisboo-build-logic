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
package com.frisboo.corebanking.extensions

import com.frisboo.corebanking.constants.CatalogVersionConstants
import com.frisboo.corebanking.constants.ConfigurationConstants
import com.frisboo.corebanking.utils.BomExtensionSpec
import com.frisboo.corebanking.utils.gradleProperty
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

/**
 * Gradle extension that exposes gRPC-related feature toggles configured via project properties.
 */
public open class GRPCExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
        libs: VersionCatalog,
    ) {
        public val enableGrpcNetty: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.GRPC.ENABLE_GRPC_NETTY,
                        String::toBoolean
                    ).orElse(true)
            )

        public val enableGrpcKotlinStub: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.GRPC.ENABLE_GRPC_KOTLIN_STUB,
                        String::toBoolean
                    ).orElse(true)
            )

        public val enableGrpcProtobuf: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.GRPC.ENABLE_GRPC_PROTOBUF,
                        String::toBoolean
                    ).orElse(true)
            )

        public val enableProtobufKotlin: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.GRPC.ENABLE_PROTOBUF_KOTLIN,
                        String::toBoolean
                    ).orElse(true)
            )

        public val enableProtobufPlugin: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.GRPC.ENABLE_PROTOBUF_PLUGIN,
                        String::toBoolean
                    ).orElse(true)
            )

        public val grpcBom: BomExtensionSpec =
            objects.newInstance(
                BomExtensionSpec::class.java,
                libs,
                CatalogVersionConstants.Libraries.GRPC_BOM,
                ConfigurationConstants.GRPC.ENABLE_GRPC_BOM,
                true
            )

        public val springBootGrpcBom: BomExtensionSpec =
            objects.newInstance(
                BomExtensionSpec::class.java,
                libs,
                CatalogVersionConstants.Libraries.SPRING_BOOT_GRPC_BOM,
                ConfigurationConstants.GRPC.ENABLE_SPRING_BOOT_GRPC_BOM,
                true
            )

        public val enableSpringBootGrpcStarter: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.GRPC.ENABLE_SPRING_BOOT_GRPC_STARTER,
                        String::toBoolean
                    ).orElse(true)
            )
    }
