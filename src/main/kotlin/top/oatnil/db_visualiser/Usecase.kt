package top.oatnil.db_visualiser

import org.jooq.Configuration
import org.jooq.ConnectionProvider
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.TableImpl
import org.postgresql.ds.PGSimpleDataSource
import top.oatnil.db_visualiser.core.MetaData
import top.oatnil.db_visualiser.core.RenderFactory
import top.oatnil.db_visualiser.core.RenderName
import top.oatnil.db_visualiser.core.generateTable
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Usecase {

    private fun prepare(): DSLContext {
        val dataSource = PGSimpleDataSource()

        dataSource.serverNames = arrayOf("localhost")
        dataSource.portNumbers = intArrayOf(5432)
        dataSource.databaseName = "postgres"
        dataSource.user = "postgres"
        dataSource.password = "password"

        val connectionProvider: ConnectionProvider = DataSourceConnectionProvider(dataSource)

        val configuration: Configuration = DefaultConfiguration()
            .set(connectionProvider)
            .set(SQLDialect.POSTGRES)

        return DSL.using(configuration)
    }

    fun generate(renderName: RenderName, jooqTables: List<TableImpl<*>>, metaData: MetaData? = null) {
        val dslContext = prepare()

        val tables = jooqTables.map { it.generateTable(dslContext) }

        val renderFactory = RenderFactory()

        writeStringToFile(renderName, renderFactory.getRender(renderName).renderTables(tables))
    }

    fun writeStringToFile(renderName: RenderName, content: String) {
        // Get the current date and time
        val currentDateTime = LocalDateTime.now()

        // Format the date and time as a string to use in the file name
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")
        val formattedDateTime = currentDateTime.format(formatter)

        // Create the file name using the formatted date and time
        val filename: String = when (renderName) {
            RenderName.DBML -> "$formattedDateTime.dbml"
            RenderName.Excalidraw -> "$formattedDateTime.excalidraw"
        }

        // Create the file object
        val file = File(filename)

        // Write the content to the file
        file.writeText(content)
    }
}
