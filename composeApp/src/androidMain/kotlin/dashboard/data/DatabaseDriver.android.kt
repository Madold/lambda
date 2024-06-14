package dashboard.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.programmingmasters.lambda.cache.LambdaDatabase

class AndroidDatabaseDriverFactory(private val context: Context): DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(LambdaDatabase.Schema, context,"LambdaDatabase" ) 
    }
}