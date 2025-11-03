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
package com.frisboo.corebanking.persistenceconvention

import com.frisboo.corebanking.convention.utils.getLibs
import com.frisboo.corebanking.convention.utils.libraryOrThrow
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class PersistenceManager(
    private val project: Project,
    private val ext: PersistenceExtension,
) {
    private val libs = project.getLibs()

    public fun configure() {
        if (!ext.enabled.get()) return

        if (ext.enabledH2.get()) {
//            configureH2()
        }

        if (ext.enabledMongo.get()) {
            configureMongo()
        }

        if (ext.enabledPostgres.get()) {
            configurePostgres()
        }

//        project.dependencies {
//            add("implementation", "org.springframework.boot:spring-boot-starter-data-r2dbc")
//            add("implementation", "org.springframework.boot:spring-boot-starter-data-redis-reactive")
//
//
//            add("runtimeOnly", libs.libraryOrThrow(CatalogVersionConstants.Libraries.H2))
//            add("runtimeOnly", "io.r2dbc:r2dbc-h2")
//            add("runtimeOnly", libs.libraryOrThrow(CatalogVersionConstants.Libraries.POSTGRESQL))
//            add("runtimeOnly", "org.postgresql:r2dbc-postgresql")
//        }
    }

    private fun configurePostgres() {
        project.dependencies {
            add("implementation", libs.libraryOrThrow("exposed-spring-boot-starter"))
            add("implementation", libs.libraryOrThrow("exposed-r2dbc"))
            add("implementation", libs.libraryOrThrow("exposed-kotlin-datetime"))
            add("implementation", libs.libraryOrThrow("h2"))
            add("runtimeOnly", libs.libraryOrThrow("postgresql"))
            add("testImplementation", libs.libraryOrThrow("testcontainers-postgresql"))
        }
    }

    private fun configureMongo() {
        project.dependencies {
            add("implementation", libs.libraryOrThrow("spring-boot-starter-data-mongodb"))
            add("implementation", libs.libraryOrThrow("mongodb"))
            add("testImplementation", libs.libraryOrThrow("testcontainers-mongodb"))

            libs.findLibrary("testcontainers-junit-jupiter").ifPresent {
                add("testImplementation", it.get())
            }
        }
    }
}
