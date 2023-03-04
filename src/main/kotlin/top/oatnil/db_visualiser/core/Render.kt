package top.oatnil.db_visualiser.core

import top.oatnil.db_visualiser.core.render.DbmlRender
import top.oatnil.db_visualiser.core.render.ExcalidrawRender
import java.io.StringWriter

enum class RenderName {
    Excalidraw,
    DBML
}

interface Render {

    fun getName(): RenderName

    fun renderTables(tables: List<Table>): String

}

class RenderFactory() {
    private val renders = mapOf(
        RenderName.DBML to DbmlRender(),
        RenderName.Excalidraw to ExcalidrawRender(StringWriter())
    )

    fun getRender(renderName: RenderName): Render {
        return renders[renderName] ?: throw Exception("Can't find the corresponding render by renderName: $renderName")
    }

}
