package di

import dashboard.data.DatabaseDriverFactory
import dashboard.data.DesktopDatabaseDriverFactory
import org.koin.dsl.module

val desktopModule = module {
    single<DatabaseDriverFactory> {
        DesktopDatabaseDriverFactory()
    }
}