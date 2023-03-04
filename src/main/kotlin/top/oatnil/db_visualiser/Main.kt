package top.oatnil.db_visualiser

import top.oatnil.db_visualiser.core.RenderName
import top.oatnil.db_visualiser.generated_sources.jooq.Tables

fun main() {
    val usecase = Usecase()
    usecase.generate(
        RenderName.Excalidraw, listOf(
            // TODO: 4/3/23 Modify the table you want to generate from
            Tables.TABLES,
            Tables.COLUMN_DOMAIN_USAGE,
            Tables.COLLATIONS,
            Tables.COLUMNS
        )
    )
}
