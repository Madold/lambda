package com.programmingmasters.lambda.di

import dashboard.data.AndroidDatabaseDriverFactory
import dashboard.data.DatabaseDriverFactory
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidAppModule = module  {
    
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(get())
    }
    
}