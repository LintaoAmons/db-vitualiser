package top.oatnil.db_visualiser.core.render

import top.oatnil.db_visualiser.core.Render
import top.oatnil.db_visualiser.core.RenderName
import top.oatnil.db_visualiser.core.Table
import java.io.StringWriter
import java.util.*
import javax.json.Json
import javax.json.JsonArrayBuilder
import javax.json.JsonObjectBuilder
import javax.json.JsonWriter
import kotlin.random.Random

class ExcalidrawRender(private val writer: StringWriter) : Render {
    private val jsonWriter: JsonWriter = Json.createWriter(writer)

    data class MetaData(
        val currentX: Double,
        val currentY: Double
    ) : top.oatnil.db_visualiser.core.MetaData

    override fun getName(): RenderName {
        return RenderName.Excalidraw
    }

    override fun renderTables(tables: List<Table>): String {
        var currentX = 80.0
        val initY = 150.0
        val elements = Json.createArrayBuilder()

        val tablesAsElementArray = tables.map {
            currentX += 500.0
            it.metaData = MetaData(currentX = currentX, currentY = initY)
            tableElement(it)
        }

        for (table in tablesAsElementArray) {
            elements.addAll(table)
        }

        // Write the JSON output
        val rootObject = Json.createObjectBuilder()
            .add("type", "excalidraw")
            .add("version", 2)
            .add(
                "source",
                Json.createObjectBuilder().add("type", "url").add("url", "https://excalidraw.com/")
            )
            .add("elements", elements)
            .build()

        jsonWriter.writeObject(rootObject)

        return writer.toString()
    }

    fun tableElement(table: Table): JsonArrayBuilder {
        var (currentX, currentY) = table.metaData as MetaData

        val groupId: String = UUID.randomUUID().toString()
        val elements = Json.createArrayBuilder()

        // Add the main table rectangle
        val mainTable = createRectangleObject(currentX, currentY - 30, 200.0, 30.0, groupId)
        elements.add(mainTable)
        val tableName = createTextObject(
            table.tableName,
            currentX,
            currentY - 30,
            16.0,
            ExcalidrawColor.ORANGE
        )
        elements.add(tableName)

        // Add the column names and types
        table.columns?.forEach { column ->
            val recTangle = createRectangleObject(currentX, currentY, 400.0, 40.0)
            elements.add(recTangle)
            val columnText = createTextObject(
                "${column.name}: ${column.dataType}",
                currentX,
                currentY,
                16.0
            )
            elements.add(columnText)
            val sampleData = createTextObject(
                "${column.dataValue}",
                currentX,
                currentY + 20,
                6.0,
                ExcalidrawColor.GRAY
            )
            elements.add(sampleData)

            currentY += 40.0
        } ?: return Json.createArrayBuilder()

        return elements
    }

    private fun createRectangleObject(
        x: Double,
        y: Double,
        width: Double,
        height: Double,
        groupId: String? = null,
    ): JsonObjectBuilder {
        val jsonObjectBuilder = Json.createObjectBuilder()
            .add("id", UUID.randomUUID().toString())
            .add("type", "rectangle")
            .add("x", x)
            .add("y", y)
            .add("width", width)
            .add("height", height)
            .add("angle", 0)
            .add("strokeColor", "#000000")
            .add("backgroundColor", "transparent")
            .add("fillStyle", "hachure")
            .add("strokeWidth", 1)
            .add("strokeStyle", "solid")
            .add("roughness", 1)
            .add("opacity", 100)
            .add("strokeSharpness", "sharp")
            .add("seed", Random.nextInt())
            .add("version", 0)
            .add("versionNonce", Random.nextInt())
            .add("isDeleted", false)
            .add("boundElementIds", Json.createArrayBuilder().build())
        return if (groupId == null) {
            jsonObjectBuilder
        } else {
            jsonObjectBuilder
                .add("groupIds", Json.createArrayBuilder().add(groupId).build())
        }
    }

    private fun createTextObject(
        text: String,
        x: Double,
        y: Double,
        fontSize: Double,
        textColor: ExcalidrawColor = ExcalidrawColor.BLACK,
        groupId: String? = null,
    ): JsonObjectBuilder {
        val jsonObjectBuilder = Json.createObjectBuilder()
            .add("type", "text")
            .add("x", x)
            .add("y", y)
            .add("width", text.length * fontSize)
            .add("height", fontSize)
            .add("angle", 0)
            .add("strokeColor", textColor.hex)
            .add("backgroundColor", "transparent")
            .add("fillStyle", "hachure")
            .add("strokeWidth", 1)
            .add("strokeStyle", "solid")
            .add("roughness", 1)
            .add("opacity", 100)
            .add("strokeSharpness", "sharp")
            .add("seed", 1559448462)
            .add("version", 118)
            .add("versionNonce", 1935339986)
            .add("isDeleted", false)
            .add("boundElementIds", Json.createArrayBuilder())
            .add("text", text)
            .add("fontSize", fontSize)
            .add("fontFamily", 1)
            .add("textAlign", "left")
            .add("verticalAlign", "middle")
            .add("baseline", fontSize - 2)
        return if (groupId == null) {
            jsonObjectBuilder
        } else {
            jsonObjectBuilder
                .add("groupIds", Json.createArrayBuilder().add(groupId).build())
        }
    }
}

enum class ExcalidrawColor(val hex: String) {
    BLACK("#000000"),
    WHITE("#FFFFFF"),
    GRAY("#888888"),
    BLUE("#3C82F6"),
    GREEN("#50C878"),
    YELLOW("#FFD700"),
    ORANGE("#c92a2a"),
    RED("#FF0000"),
    PINK("#FFC0CB"),
    PURPLE("#800080");

    companion object {
        fun fromHex(hex: String): ExcalidrawColor? {
            return values().find { it.hex == hex }
        }
    }
}
