package di

import dashboard.data.local.UsersRepositoryImpl
import dashboard.domain.local.UsersRepository
import org.koin.dsl.module

val dashboardModule = module {
    
    single<UsersRepository> {
        UsersRepositoryImpl(get())
    }
    
}