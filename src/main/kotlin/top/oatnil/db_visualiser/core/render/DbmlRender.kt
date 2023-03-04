package top.oatnil.db_visualiser.core.render

import top.oatnil.db_visualiser.core.Column
import top.oatnil.db_visualiser.core.Render
import top.oatnil.db_visualiser.core.RenderName
import top.oatnil.db_visualiser.core.Table


class DbmlRender : Render {
    override fun getName(): RenderName {
        return RenderName.DBML
    }

    override fun renderTables(tables: List<Table>): String {
        return tables.joinToString("\n") {
            render(it)
        }
    }

    private fun render(table: Table): String {
        return "TABLE ${table.tableName} {\n ${table.columns?.render()} \n}\n"
    }

    private fun List<Column>.render(): String {
        return this.joinToString("\n") { it.renderColumn() }
    }

    private fun Column.renderColumn(): String {
        return "$name $dataType [note: \"sample data: $dataValue\"]"
    }

}
