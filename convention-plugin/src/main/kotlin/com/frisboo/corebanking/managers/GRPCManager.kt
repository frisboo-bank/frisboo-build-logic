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
package com.frisboo.corebanking.managers

import com.frisboo.corebanking.constants.CatalogVersionConstants
import com.frisboo.corebanking.extensions.GRPCExtension
import com.frisboo.corebanking.utils.addImplementation
import com.frisboo.corebanking.utils.addTestImplementation
import com.frisboo.corebanking.utils.applyBomIfEnabled
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
import com.frisboo.corebanking.utils.pluginIdOrThrow
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.dependencies

public class GRPCManager(
    private val project: Project,
    private val ext: GRPCExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        logger.info("-----------------------------------------------------------")
        logger.info("Configuring GRPC settings for project {}", project.name)
        logger.info(" - Protobuf Plugin Enabled: {}", ext.enableProtobufPlugin.get())
        logger.info(" - GRPC Spring Boot BOM Enabled: {}", ext.springBootGrpcBom.enabled.get())
        logger.info("-----------------------------------------------------------")

        if (ext.enableProtobufPlugin.get()) {
            project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.PROTOBUF))
        }

        project.dependencies {
            applyBomIfEnabled(
                ext.grpcBom,
                ext.springBootGrpcBom
            )

            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.GRPC_SERVICES))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.GRPC_SERVLET))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.GRPC_STUB))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.PROTOBUF_JAVA))
            addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.PROTOBUF_JAVA_UTIL))

            if (ext.enableGrpcKotlinStub.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.GRPC_KOTLIN_STUB))
            }
            if (ext.enableGrpcNetty.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.GRPC_NETTY_SHADED))
            }
            if (ext.enableGrpcProtobuf.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.GRPC_PROTOBUF))
            }
            if (ext.enableProtobufKotlin.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.PROTOBUF_KOTLIN))
            }
            if (ext.enableSpringBootGrpcStarter.get()) {
                addImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_GRPC_STARTER))
                addTestImplementation(libs.libraryOrThrow(CatalogVersionConstants.Libraries.SPRING_BOOT_GRPC_TEST))
            }
        }
    }
}
