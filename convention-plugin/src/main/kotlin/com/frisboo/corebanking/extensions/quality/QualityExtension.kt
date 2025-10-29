package com.frisboo.corebanking.extensions.quality

import com.frisboo.corebanking.ConfigurationConstants
import com.frisboo.corebanking.utils.gradleProperty
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public open class QualityExtension
@Inject constructor(
    objects: ObjectFactory,
    providers: ProviderFactory,
) {
    public val enabled: Property<Boolean> = objects.property<Boolean>().convention(
        providers.gradleProperty(ConfigurationConstants.Quality.ENABLE_QUALITY, String::toBoolean).orElse(true),
    )
    public val cpd: CpdExtension = objects.newInstance()
    public val detekt: DetektExtension = objects.newInstance()
    public val dokka: DokkaExtension = objects.newInstance()
    public val jacoco: JacocoExtension = objects.newInstance()
    public val kover: KoverExtension = objects.newInstance()
    public val owaspDependencyCheck: OwaspDepCheckExtension = objects.newInstance()
    public val pitest: PitestExtension = objects.newInstance()
    public val pmd: PmdExtension = objects.newInstance()
    public val spotbugs: SpotbugsExtension = objects.newInstance()
    public val spotless: SpotlessExtension = objects.newInstance()

    public fun enabled(value: Boolean): Unit = enabled.set(value)

    public fun cpd(action: Action<CpdExtension>): Unit = action.execute(cpd)
    public fun detekt(action: Action<DetektExtension>): Unit = action.execute(detekt)
    public fun dokka(action: Action<DokkaExtension>): Unit = action.execute(dokka)
    public fun jacoco(action: Action<JacocoExtension>): Unit = action.execute(jacoco)
    public fun kover(action: Action<KoverExtension>): Unit = action.execute(kover)
    public fun owaspDependencyCheck(action: Action<OwaspDepCheckExtension>): Unit = action.execute(owaspDependencyCheck)
    public fun pitest(action: Action<PitestExtension>): Unit = action.execute(pitest)
    public fun pmd(action: Action<PmdExtension>): Unit = action.execute(pmd)
    public fun spotbugs(action: Action<SpotbugsExtension>): Unit = action.execute(spotbugs)
    public fun spotless(action: Action<SpotlessExtension>): Unit = action.execute(spotless)

    public companion object {
        public const val DEFAULT_CODE_COVERAGE_THRESHOLD: Double = 0.8
        public const val DEFAULT_KOVER_THRESHOLD: Int = 80
        public const val DEFAULT_MUTATION_THRESHOLD: Int = 85
        public const val DEFAULT_OWASP_THRESHOLD: Float = 7.0f
        public const val DEFAULT_THREADS: Int = 4
        public const val DEFAULT_TOKEN_COUNT: Int = 50
    }
}
