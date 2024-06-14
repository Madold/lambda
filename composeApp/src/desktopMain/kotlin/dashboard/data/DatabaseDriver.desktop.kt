package dashboard.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.programmingmasters.lambda.cache.LambdaDatabase

class DesktopDatabaseDriverFactory: DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        //TODO: Change this behavior to  a persistent database
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        LambdaDatabase.Schema.create(driver)
        return driver
    }
}