package di

import dashboard.presentation.DashboardViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class DesktopViewModelModule: ViewModelModule {
    override fun get(): Module {
        return module {
            singleOf(::DashboardViewModel)
        }
    }

}
