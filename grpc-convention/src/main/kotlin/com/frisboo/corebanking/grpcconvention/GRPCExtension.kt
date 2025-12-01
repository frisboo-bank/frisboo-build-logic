// kotlin
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

import com.frisboo.corebanking.coreconvention.CatalogVersionConstants
import com.frisboo.corebanking.coreconvention.utils.BomExtensionSpec
import com.frisboo.corebanking.coreconvention.utils.gradleProperty
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
    /**
     * Toggle for enabling gRPC Netty transport.
     * Reads project property defined by [GRPCConstants.Configuration.ENABLE_GRPC_NETTY].
     * Defaults to `true` when the property is not present.
     */
    public val enableGrpcNetty: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            GRPCConstants.Configuration.ENABLE_GRPC_NETTY,
            String::toBoolean,
        ).orElse(true),
    )

    /**
     * Toggle for enabling Kotlin gRPC stubs generation/usage.
     * Reads project property defined by [GRPCConstants.Configuration.ENABLE_GRPC_KOTLIN_STUB].
     * Defaults to `true` when the property is not present.
     */
    public val enableGrpcKotlinStub: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            GRPCConstants.Configuration.ENABLE_GRPC_KOTLIN_STUB,
            String::toBoolean,
        ).orElse(true),
    )

    /**
     * Toggle for enabling gRPC Protobuf integration.
     * Reads project property defined by [GRPCConstants.Configuration.ENABLE_GRPC_PROTOBUF].
     * Defaults to `true` when the property is not present.
     */
    public val enableGrpcProtobuf: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            GRPCConstants.Configuration.ENABLE_GRPC_PROTOBUF,
            String::toBoolean,
        ).orElse(true),
    )

    /**
     * Toggle for enabling Protobuf Kotlin support.
     * Reads project property defined by [GRPCConstants.Configuration.ENABLE_PROTOBUF_KOTLIN].
     * Defaults to `true` when the property is not present.
     */
    public val enableProtobufKotlin: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            GRPCConstants.Configuration.ENABLE_PROTOBUF_KOTLIN,
            String::toBoolean,
        ).orElse(true),
    )

    /**
     * Toggle for enabling the Protobuf Gradle plugin.
     * Reads project property defined by [GRPCConstants.Configuration.ENABLE_PROTOBUF_PLUGIN].
     * Defaults to `true` when the property is not present.
     */
    public val enableProtobufPlugin: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            GRPCConstants.Configuration.ENABLE_PROTOBUF_PLUGIN,
            String::toBoolean,
        ).orElse(true),
    )

    /**
     * BOM specification for the gRPC library.
     * Controlled by the project property defined in
     * [GRPCConstants.Configuration.ENABLE_GRPC_BOM].
     */
    public val grpcBom: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        CatalogVersionConstants.Libraries.GRPC_BOM,
        GRPCConstants.Configuration.ENABLE_GRPC_BOM,
        true,
    )

    /**
     * BOM specification for the gRPC Spring Boot Starter library.
     * Controlled by the project property defined in
     * [GRPCConstants.Configuration.ENABLE_SPRING_BOOT_GRPC_BOM].
     */
    public val springBootGrpcBom: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        CatalogVersionConstants.Libraries.SPRING_BOOT_GRPC_BOM,
        GRPCConstants.Configuration.ENABLE_SPRING_BOOT_GRPC_BOM,
        true,
    )

    /**
     * Toggle for enabling gRPC Spring Boot Starter integration.
     * Reads project property defined by [GRPCConstants.Configuration.ENABLE_SPRING_BOOT_GRPC_STARTER].
     * Defaults to `true` when the property is not present.
     */
    public val enableSpringBootGrpcStarter: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(
            GRPCConstants.Configuration.ENABLE_SPRING_BOOT_GRPC_STARTER,
            String::toBoolean,
        ).orElse(true),
    )
}
