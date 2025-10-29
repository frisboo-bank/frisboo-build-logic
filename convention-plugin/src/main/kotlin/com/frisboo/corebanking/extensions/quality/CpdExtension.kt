package com.frisboo.corebanking.extensions.quality

import com.frisboo.corebanking.ConfigurationConstants
import com.frisboo.corebanking.extensions.quality.QualityExtension.Companion.DEFAULT_TOKEN_COUNT
import com.frisboo.corebanking.utils.gradleProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class CpdExtension
@Inject constructor(objects: ObjectFactory, providers: ProviderFactory) {
    public val enabled: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(ConfigurationConstants.Quality.CPD_ENABLED, String::toBoolean).orElse(true),
    )
    public val failOnViolation: Property<Boolean> = objects.property<Boolean>().convention(true)
    public val source: Property<FileCollection> =
        objects.property<FileCollection>().convention(objects.fileCollection())
    public val minimumTokenCount: Property<Int> = objects.property<Int>().convention(DEFAULT_TOKEN_COUNT)

    public fun enabled(value: Boolean): Unit = enabled.set(value)
    public fun failOnViolation(value: Boolean): Unit = failOnViolation.set(value)
    public fun source(path: FileCollection): Unit = source.set(path)
    public fun minimumTokenCount(value: Int): Unit = minimumTokenCount.set(value)
}
