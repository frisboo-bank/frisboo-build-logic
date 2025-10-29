package com.frisboo.corebanking.managers

import com.frisboo.corebanking.extensions.PersistenceExtension
import com.frisboo.corebanking.utils.getLibs
import com.frisboo.corebanking.utils.libraryOrThrow
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
//            add("runtimeOnly", libs.libraryOrThrow(DependencyConstants.Libraries.H2))
//            add("runtimeOnly", "io.r2dbc:r2dbc-h2")
//            add("runtimeOnly", libs.libraryOrThrow(DependencyConstants.Libraries.POSTGRESQL))
//            add("runtimeOnly", "org.postgresql:r2dbc-postgresql")
//        }
    }

    private fun configurePostgres() {
        project.dependencies {
            add("implementation", platform(libs.libraryOrThrow("exposed-bom")))

            add("implementation", libs.libraryOrThrow("exposed-spring-boot-starter"))
            add("implementation", libs.libraryOrThrow("exposed-jdbc"))
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
