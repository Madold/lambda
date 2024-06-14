package com.programmingmasters.lambda

import android.app.Application
import com.programmingmasters.lambda.di.AndroidViewModelModule
import com.programmingmasters.lambda.di.androidAppModule
import di.dashboardModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApp)
            modules(androidAppModule, dashboardModule, AndroidViewModelModule().get())
        }
    }
    
}
