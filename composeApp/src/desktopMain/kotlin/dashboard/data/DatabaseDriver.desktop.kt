package dashboard.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.programmingmasters.lambda.cache.LambdaDatabase
import java.io.File

class DesktopDatabaseDriverFactory: DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        val parentFolder = File(System.getProperty("user.home") + "/LambdaAdminPanel")

        if (!parentFolder.exists()) {
            parentFolder.mkdirs()
        }

        val databasePath = File(parentFolder, "LambdaDatabase.db")
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
        
        if (!databasePath.exists() || databasePath.length() == 0L) {
            LambdaDatabase.Schema.create(driver)
        }

        return driver

    }
}