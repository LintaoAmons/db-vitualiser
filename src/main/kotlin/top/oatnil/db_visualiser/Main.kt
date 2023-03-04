package top.oatnil.db_visualiser

import top.oatnil.db_visualiser.core.RenderName
import top.oatnil.db_visualiser.generated_sources.jooq.Tables
import java.util.*

fun main() {
    val usecase = Usecase()
    usecase.generate(
        RenderName.Excalidraw, Arrays.asList(
            Tables.TABLES,
            Tables.COLUMN_DOMAIN_USAGE,
            Tables.COLLATIONS,
            Tables.COLUMNS
        )
    )
}
