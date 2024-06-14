package com.programmingmasters.lambda.di

import dashboard.presentation.DashboardViewModel
import di.ViewModelModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidViewModelModule: ViewModelModule {
    override fun get(): Module {
        return module {
            viewModelOf(::DashboardViewModel)
        }
    }

}
