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
package com.frisboo.corebanking

import com.frisboo.corebanking.utils.TestProjectBuilder
import com.frisboo.corebanking.utils.mockBuildScript
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotContain
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName("Plugin Manager Integration Test")
class PluginManagerIntegrationTest {
    lateinit var builder: TestProjectBuilder

    @Test
    @DisplayName("should apply convention plugin to Kotlin DSL successfully")
    fun `should apply convention plugin to Kotlin DSL successfully`() {
        builder =
            TestProjectBuilder
                .create()
                .withVersionCatalog()
                .withSettingsGradle()
                .withBuildGradle(mockBuildScript())
                .withKotlinSource()

        val result = builder.runGradle("help")

        result.task(":help")?.outcome shouldBe TaskOutcome.SUCCESS
        result.output shouldNotContain "FAILED"
        result.output shouldNotContain "Exception"
    }
}
