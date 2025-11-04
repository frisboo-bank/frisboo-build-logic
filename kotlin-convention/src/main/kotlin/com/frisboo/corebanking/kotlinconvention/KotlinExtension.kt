package com.frisboo.corebanking.kotlinconvention

import com.frisboo.corebanking.convention.extensions.boms.BomExtensionSpec
import org.gradle.api.Action
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ProviderFactory
import javax.inject.Inject

public open class KotlinExtension
@Inject constructor(
    objects: ObjectFactory,
    providers: ProviderFactory,
    libs: VersionCatalog,
) {
    public val arrowKtBom: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        "arrow-kt-bom",
        KotlinConstants.Configuration.ARROW_KT_BOM_ENABLED,
        false,
    )
    public val kotlinBom: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        "kotlin-bom",
        KotlinConstants.Configuration.KOTLIN_BOM_ENABLED,
        false,
    )
    public val kotlinxCoroutinesBom: BomExtensionSpec = objects.newInstance(
        BomExtensionSpec::class.java,
        libs,
        "kotlinx-coroutines-bom",
        KotlinConstants.Configuration.KOTLINX_COROUTINES_BOM_ENABLED,
        false,
    )

    public fun arrowKtBom(action: Action<BomExtensionSpec>): Unit = action.execute(arrowKtBom)
    public fun kotlinBom(action: Action<BomExtensionSpec>): Unit = action.execute(kotlinBom)
    public fun kotlinxCoroutinesBom(action: Action<BomExtensionSpec>): Unit = action.execute(kotlinxCoroutinesBom)
}
