package com.frisboo.corebanking.extensions.quality

import com.frisboo.corebanking.ConfigurationConstants.Quality
import com.frisboo.corebanking.utils.gradleProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class DetektExtension
@Inject constructor(objects: ObjectFactory, providers: ProviderFactory) {
    public val enabled: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(Quality.DETEKT_ENABLED, String::toBoolean).orElse(true),
    )
    public val autoCorrect: Property<Boolean> = objects.property<Boolean>().convention(false)
    public val failOnViolation: Property<Boolean> = objects.property<Boolean>().convention(true)
    public val source: Property<FileCollection> =
        objects.property<FileCollection>().convention(objects.fileCollection())
}
