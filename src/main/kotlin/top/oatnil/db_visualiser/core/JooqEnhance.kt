package top.oatnil.db_visualiser.core

import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Table
import top.oatnil.db_visualiser.core.ColumnDefinition.Companion.toColumnDefinition
import java.beans.Introspector

fun Table<*>.generateTable(dslContext: DSLContext): top.oatnil.db_visualiser.core.Table {
    val recordInstance = this.selectAny(dslContext)
    return Table(
        recordInstance?.tableName() ?: "Missing Table Name: [$type]",
        recordInstance?.columns(),
    )
}

fun <R : Record> Table<R>.selectAny(dslContext: DSLContext): R? {
    return dslContext.selectFrom(this)
        .limit(1)
        .fetchAny()
}

fun Record.generateTable(): top.oatnil.db_visualiser.core.Table {
    return Table(tableName(), columns())
}

fun Record.tableName(): String {
    return this::class.java.simpleName.replace("Record", "")
}

fun Record.columns(): List<Column> {
    return this::class.java.toColumns(this)
}

fun Class<out Record>.toColumnDefinitions(): List<ColumnDefinition> {
    return Introspector.getBeanInfo(this)
        .propertyDescriptors
        .filterNot { listOf("class", "value", "table", "SQLTypeName", "qualifier").contains(it.name) }
        .map { it.toColumnDefinition() }
}

fun Class<out Record>.toColumns(dataSource: Record): List<Column> {
    return this.toColumnDefinitions()
        .map { it.toColumn(dataSource) }
}
