package com.solutionium.feature.login

import com.solutionium.domain.user.getUserDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getLoginModules() = listOf(loginModule) + getUserDomainModules()


val loginModule = module {
    viewModel {
        LoginViewModel(
            requestOtpUseCase = get(),
            loginOrRegisterUseCase = get(),
            checkLoginUserUseCase = get(),
            editUserDetailsUseCase = get()
        )
    }
}
