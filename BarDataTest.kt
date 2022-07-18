package prometer.gen.html.data

import org.assertj.core.api.Assertions
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

class BarDataTest {
    private val resourceDirectory = Paths.get("src", "main", "resources")
    private val validFolder = resourceDirectory.resolve("templates").toFile()


@Nested
inner class  CommonUseCased {

    @Test
    fun `construct with default parameters`() {
        val barData = BarData()
        Assertions.assertThat(barData).isInstanceOf(BarData::class.java)
        Assertions.assertThat(barData.valueTitle).isEqualTo(BarData.defaultValueTitle)
        Assertions.assertThat(barData.valueFormat).isEqualTo(PlotData.NumberFormat.INTEGER)
        Assertions.assertThat(barData.orientation).isEqualTo(Orientation.VERTICAL)
    }

    @Test
    fun `construct with parameters`() {
        val title = "Test Count"
        val barData = BarData(title, PlotData.NumberFormat.DOUBLE, Orientation.VERTICAL)
        Assertions.assertThat(barData).isInstanceOf(BarData::class.java)
        Assertions.assertThat(barData.valueTitle).isEqualTo(title)
        Assertions.assertThat(barData.valueFormat).isEqualTo(PlotData.NumberFormat.DOUBLE)
        Assertions.assertThat(barData.orientation).isEqualTo(Orientation.VERTICAL)
    }

    @Nested
    inner class HTMLGeneration {
        @Test
        fun `Generate HTML Page for Empty page`() {
            val config = getHTMLConfig(validFolder)

            val barData = BarData()

            barData.valueMap["Good"] = 60.0
            barData.valueMap["Best"] = 20.0
            barData.valueMap["Better"] = 50.0


            val page = Page(
                "A sample page",
                "A simple page for experimentation",
                sections = listOf(
                    Section(
                        "BAR Data Test",
                        contents = listOf(PlotContent("BAR Data", barData))
                    )
                ),
                fileName = "bar_test",
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
                testHTMLDir.resolve("bar_test.html").toFile(),
                "test_page.ftlh"
            )
            config.createJSPage(
                page,
                testJSDir.resolve("bar_test.js").toFile()
            )

        }

    }
}

}