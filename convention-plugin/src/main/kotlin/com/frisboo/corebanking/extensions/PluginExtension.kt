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

import com.frisboo.corebanking.constants.ConfigurationConstants
import com.frisboo.corebanking.utils.gradleProperty
import de.skuzzle.restrictimports.gradle.RestrictImportsExtension
import org.gradle.api.Action
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class PluginExtension
    @Inject
    constructor(
        objects: ObjectFactory,
        providers: ProviderFactory,
        layout: ProjectLayout,
        libs: VersionCatalog,
    ) {
        private val projectName = layout.projectDirectory.asFile.name

        public val conventionPluginVersion: Property<String> =
            objects.property<String>().convention(
                providers.gradleProperty("corebanking-convention")
            )

        public val artifactId: Property<String> =
            objects.property<String>().convention(
                projectName.removePrefix("frisboo-corebanking-").removeSuffix("-plugin")
            )

        public val displayName: Property<String> =
            objects.property<String>().convention(
                artifactId.map { id ->
                    id.split("-").joinToString(" ") { part -> part.replaceFirstChar { it.titlecase() } }
                }
            )

        public val enableCaching: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_CACHING,
                        String::toBoolean
                    ).orElse(false)
            )

        public val enableGRPC: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_GRPC,
                        String::toBoolean
                    ).orElse(false)
            )

        public val enableMessaging: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_MESSAGING,
                        String::toBoolean
                    ).orElse(false)
            )

        public val enableOpenapi: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_OPENAPI,
                        String::toBoolean
                    ).orElse(false)
            )

        public val enablePersistence: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_PERSISTENCE,
                        String::toBoolean
                    ).orElse(false)
            )

        public val enableQuality: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_QUALITY,
                        String::toBoolean
                    ).orElse(true)
            )

        public val enableSpringBoot: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_SPRING_BOOT,
                        String::toBoolean
                    ).orElse(true)
            )

        public val enableTelemetry: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_TELEMETRY,
                        String::toBoolean
                    ).orElse(false)
            )

        public val enableTesting: Property<Boolean> =
            objects.property<Boolean>().convention(
                providers
                    .gradleProperty(
                        ConfigurationConstants.Plugin.ENABLE_TESTING,
                        String::toBoolean
                    ).orElse(true)
            )

        public val caching: CachingExtension = objects.newInstance<CachingExtension>(libs)
        public val grpc: GRPCExtension = objects.newInstance<GRPCExtension>(libs)
        public val kotlin: KotlinExtension = objects.newInstance<KotlinExtension>(libs)
        public val messaging: MessagingExtension = objects.newInstance<MessagingExtension>(libs)
        public val openapi: OpenapiExtension = objects.newInstance<OpenapiExtension>(libs)
        public val persistence: PersistenceExtension = objects.newInstance<PersistenceExtension>(libs)
        public val quality: QualityExtension = objects.newInstance<QualityExtension>(libs)
        public val restrictImports: RestrictImportsExtension =
            objects.newInstance<RestrictImportsExtension>().apply {
                reason.convention("Please use JUnit 5 (JUnit Jupiter) instead of JUnit 4")
                bannedImports.convention(listOf("org.junit.**"))
                allowedImports.convention(listOf("org.junit.jupiter.**"))
            }
        public val springBoot: SpringBootExtension = objects.newInstance<SpringBootExtension>(libs)
        public val testing: TestingExtension = objects.newInstance<TestingExtension>(libs)

        public fun enableCaching(value: Boolean): Unit = enableCaching.set(value)

        public fun enableGRPC(value: Boolean): Unit = enableGRPC.set(value)

        public fun enableMessaging(value: Boolean): Unit = enableMessaging.set(value)

        public fun enableOpenapi(value: Boolean): Unit = enableOpenapi.set(value)

        public fun enablePersistence(value: Boolean): Unit = enablePersistence.set(value)

        public fun enableQuality(value: Boolean): Unit = enableQuality.set(value)

        public fun enableSpringBoot(value: Boolean): Unit = enableSpringBoot.set(value)

        public fun enableTelemetry(value: Boolean): Unit = enableTelemetry.set(value)

        public fun enableTesting(value: Boolean): Unit = enableTesting.set(value)

        public fun caching(action: Action<CachingExtension>): Unit = action.execute(caching)

        public fun grpc(action: Action<GRPCExtension>): Unit = action.execute(grpc)

        public fun kotlin(action: Action<KotlinExtension>): Unit = action.execute(kotlin)

        public fun messaging(action: Action<MessagingExtension>): Unit = action.execute(messaging)

        public fun openapi(action: Action<OpenapiExtension>): Unit = action.execute(openapi)

        public fun persistence(action: Action<PersistenceExtension>): Unit = action.execute(persistence)

        public fun quality(action: Action<QualityExtension>): Unit = action.execute(quality)

        public fun restrictImports(action: Action<RestrictImportsExtension>): Unit = action.execute(restrictImports)

        public fun springBoot(action: Action<SpringBootExtension>): Unit = action.execute(springBoot)

        public fun testing(action: Action<TestingExtension>): Unit = action.execute(testing)
    }
