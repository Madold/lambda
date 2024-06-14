package di

import org.koin.core.module.Module

interface ViewModelModule {
    fun get(): Module
}