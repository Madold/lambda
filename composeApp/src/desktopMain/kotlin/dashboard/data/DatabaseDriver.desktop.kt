package dashboard.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.programmingmasters.lambda.cache.LambdaDatabase

actual class DatabaseDriver {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        LambdaDatabase.Schema.create(driver)
        return driver
    }
}