
package prometer.gen.html.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import prometer.gen.html.UTF8_CHARSET_STRING
import prometer.gen.html.content.Page
import prometer.gen.html.content.PlotContent
import prometer.gen.html.content.Section
import prometer.gen.html.createHTMLPage
import prometer.gen.html.createJSPage
import prometer.gen.html.getHTMLConfig
import java.nio.file.Paths

class PieDataTest {

    private val resourceDirectory = Paths.get("src", "main", "resources")
    private val validFolder = resourceDirectory.resolve("templates").toFile()


    @Nested
    inner class CommonUseCases {


        @Test
        fun `construct with default parameters`() {
            val pieData = PieData()
            assertThat(pieData).isInstanceOf(PieData::class.java)
            assertThat(pieData.valueTitle).isEqualTo(PieData.defaultValueTitle)
            assertThat(pieData.valueFormat).isEqualTo(PlotData.NumberFormat.INTEGER)
        }

        @Test
        fun `construct with parameters`() {
            val title = "Test Count"
            val pieData = PieData(title, PlotData.NumberFormat.DOUBLE)
            assertThat(pieData).isInstanceOf(PieData::class.java)
            assertThat(pieData.valueTitle).isEqualTo(title)
            assertThat(pieData.valueFormat).isEqualTo(PlotData.NumberFormat.DOUBLE)
        }

    }

    @Nested
    inner class HTMLGeneration {
        @Test
        fun `generate html for empty page`() {
            val config = getHTMLConfig(validFolder)

            val pieData = PieData()
            pieData.valueMap["Good"] = 60.0
            pieData.valueMap["Better"] = 20.0
            pieData.valueMap["Besty"] = 20.0


            val page = Page(
                "A sample page",
                "A simple page for experimentation",
                sections = listOf(
                    Section(
                        "PIE Data Test",
                        contents = listOf(PlotContent("PIE Data", pieData))
                    )
                ),
                fileName = "pie_test",
                charset = UTF8_CHARSET_STRING
            )

            val testHTMLDir = Paths.get(
                "src", "test", "out", "ExampleServer", "projects", "project_xxx",
                "sandbox", "report_yyyymmdd_hhmm", "html"
            )

            val testJSDir = Paths.get(
                "src", "test", "out", "ExampleServer", "projects", "project_xxx",
                "sandbox", "report_yyyymmdd_hhmm", "js"
            )
            //copyDependencies(testOutDir.resolve("assets").toFile())


            config.createHTMLPage(
                page,
                testHTMLDir.resolve("pie_test.html").toFile(),
                "test_page.ftlh"
            )
            config.createJSPage(
                page,
                testJSDir.resolve("pie_test.js").toFile()
            )

        }
    }

}