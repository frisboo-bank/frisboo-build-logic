import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    base
    `java-gradle-plugin`
    `version-catalog`
    `kotlin-dsl`
}

java {
    val jvmTargetVersion = libs.versions.jvm.target.version
    toolchain {
//        languageVersion.set(jvmTargetVersion.map(JavaLanguageVersion::of))
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}

kotlin {
    val kotlinVersion = libs.versions.kotlin.language.version
    val jvmTargetVersion = libs.versions.jvm.target.version

    jvmToolchain {
//        languageVersion.set(jvmTargetVersion.map(JavaLanguageVersion::of))
        languageVersion.set(JavaLanguageVersion.of(24))
    }

    explicitApi()

    compilerOptions {
//        apiVersion.set(kotlinVersion.map(KotlinVersion::fromVersion))
//        languageVersion.set(kotlinVersion.map(KotlinVersion::fromVersion))
//        jvmTarget.set(jvmTargetVersion.map(JvmTarget::fromTarget))
        apiVersion.set(KotlinVersion.KOTLIN_2_3)
        languageVersion.set(KotlinVersion.KOTLIN_2_3)
        jvmTarget.set(JvmTarget.JVM_24)

        allWarningsAsErrors.set(true)
        progressiveMode.set(true)
        optIn.add("kotlin.RequiresOptIn")
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

dependencies {
    implementation(plugin(libs.plugins.kotlin.jvm))
    implementation(plugin(libs.plugins.spotless))
    implementation(plugin(libs.plugins.detekt))
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

