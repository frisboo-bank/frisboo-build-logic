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
import io.kotest.matchers.paths.shouldExist
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.xml.parsers.DocumentBuilderFactory

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName("Quality Manager Integration Test")
class QualityManagerIntegrationTest {
    lateinit var builder: TestProjectBuilder

    @Test
    @DisplayName("should execute detekt with defaults")
    fun `should execute detekt with defaults`() {
        builder =
            TestProjectBuilder
                .create()
                .withVersionCatalog()
                .withSettingsGradle()
                .withBuildGradle(mockBuildScript())
                .withKotlinSource(
                    content =
                        """
                        package com.frisboo.corebanking

                        class KotlinTestClass {
                            fun veryLongMethodNameThatExceedsTheMaximumLineLength(): String {
                                return "This line is too long and should trigger a Detekt violation"
                            }
                        }

                        """.trimIndent()
                )

        val result = builder.runGradleAndFail("detekt")
        result.task(":detekt")?.outcome shouldBe TaskOutcome.FAILED

        val xmlReport = builder.projectDir.resolve("build/reports/detekt/detekt.xml")
        val htmlReport = builder.projectDir.resolve("build/reports/detekt/detekt.html")
        val sarifReport = builder.projectDir.resolve("build/reports/detekt/detekt.sarif")
        xmlReport.shouldExist()
        htmlReport.shouldExist()
        sarifReport.shouldExist()

        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlReport.toFile())
        val violations = document.getElementsByTagName("error")

        violations.length shouldBe 1
    }
}
