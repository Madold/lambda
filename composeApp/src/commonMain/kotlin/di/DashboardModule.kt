package di

import dashboard.data.local.MentoringRepositoryImpl
import dashboard.data.local.UsersRepositoryImpl
import dashboard.domain.local.MentoringRepository
import dashboard.domain.local.UsersRepository
import dashboard.domain.use_cases.ValidateEmail
import dashboard.domain.use_cases.ValidateId
import dashboard.domain.use_cases.ValidateLastName
import dashboard.domain.use_cases.ValidateName
import org.koin.dsl.module

val dashboardModule = module {
    
    single<UsersRepository> {
        UsersRepositoryImpl(get())
    }
    
    single<ValidateId> {
        ValidateId()
    }

    single<ValidateName> {
        ValidateName()
    }

    single<ValidateLastName> {
        ValidateLastName()
    }

    single<ValidateEmail> {
        ValidateEmail()
    }
    
    single<MentoringRepository> {
        MentoringRepositoryImpl(get())
    }

}