package com.frisboo.corebanking.extensions.quality

import com.frisboo.corebanking.ConfigurationConstants
import com.frisboo.corebanking.utils.gradleProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class SpotbugsExtension
@Inject constructor(objects: ObjectFactory, providers: ProviderFactory) {
    public val enabled: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(ConfigurationConstants.Quality.SPOTBUGS_ENABLED, String::toBoolean).orElse(true),
    )
}
