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
import com.frisboo.corebanking.coreconvention.utils.addImplementation
import com.frisboo.corebanking.coreconvention.utils.addTestImplementation
import com.frisboo.corebanking.coreconvention.utils.applyBomIfEnabled
import com.frisboo.corebanking.coreconvention.utils.getLibs
import com.frisboo.corebanking.coreconvention.utils.libraryOrThrow
import com.frisboo.corebanking.coreconvention.utils.pluginIdOrThrow
import com.google.protobuf.gradle.ProtobufExtension
import com.google.protobuf.gradle.id
import org.gradle.api.Project
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

public class GRPCManager(
    private val project: Project,
    private val ext: GRPCExtension,
) {

    private val libs = project.getLibs()

    public fun configure() {
        logger.info("-----------------------------------------------------------")
        logger.info("Configuring Persistence settings for project {}", project.name)
        logger.info(" - Protobuf Plugin Enabled: {}", ext.enableProtobufPlugin.get())
        logger.info(" - GRPC Spring Boot BOM Enabled: {}", ext.springBootGrpcBom.enabled.get())
        logger.info("-----------------------------------------------------------")

        project.pluginManager.apply(libs.pluginIdOrThrow(CatalogVersionConstants.Plugins.PROTOBUF))

        project.dependencies {
            applyBomIfEnabled(
                ext.grpcBom,
                ext.springBootGrpcBom,
            )

            arrayOf(
                CatalogVersionConstants.Libraries.CONTEXT_PROPAGATION,
                CatalogVersionConstants.Libraries.GRPC_KOTLIN_STUB,
                CatalogVersionConstants.Libraries.GRPC_NETTY_SHADED,
                CatalogVersionConstants.Libraries.GRPC_PROTOBUF,
                CatalogVersionConstants.Libraries.GRPC_SERVICES,
                CatalogVersionConstants.Libraries.GRPC_SERVLET,
                CatalogVersionConstants.Libraries.GRPC_STUB,
                CatalogVersionConstants.Libraries.PROTOBUF_JAVA,
                CatalogVersionConstants.Libraries.PROTOBUF_JAVA_UTIL,
                CatalogVersionConstants.Libraries.PROTOBUF_KOTLIN,
                CatalogVersionConstants.Libraries.PROTOVALIDATE,
                CatalogVersionConstants.Libraries.SPRING_BOOT_GRPC_STARTER,
            ).forEach { addImplementation(libs.libraryOrThrow(it)) }

            arrayOf(
                CatalogVersionConstants.Libraries.SPRING_BOOT_GRPC_TEST,
            ).forEach { addTestImplementation(libs.libraryOrThrow(it)) }
        }


        project.configure<ProtobufExtension> {
            protoc {
                it.artifact = libs.libraryOrThrow(CatalogVersionConstants.Libraries.PROTOC).get().toString()
            }
            plugins {
                it.id("grpc") {
                    artifact =
                        libs.libraryOrThrow(CatalogVersionConstants.Libraries.PROTOC_GEN_GRPC_JAVA).get().toString()
                }
                it.id("grpckt") {
                    artifact = "${
                        libs.libraryOrThrow(CatalogVersionConstants.Libraries.PROTOC_GEN_GRPC_KOTLIN).get()
                    }:jdk8@jar"
                }
            }
            generateProtoTasks {
                it.all().forEach { task ->
                    task.plugins { plugin ->
                        plugin.id("grpc") {
                            option("@generated=omit")
                        }
                        plugin.id("grpckt") {
                            option("@generated=omit")
                        }
                    }
                    task.builtins { cfg ->
                        cfg.id("kotlin")
                    }
                }
            }
        }
    }
}
