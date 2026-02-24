package com.solutionium.feature.account

import com.solutionium.shared.domain.config.getConfigDomainModules
import com.solutionium.shared.domain.favorite.getFavoriteDomainModules
import com.solutionium.domain.user.getUserDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getAccountModules() = setOf(accountModule) + getUserDomainModules() + getFavoriteDomainModules() + getConfigDomainModules()

val accountModule = module {
    viewModel {
        AccountViewModel(
            checkLoginUserUseCase = get(),
            sendOtpUseCase = get(),
            loginOrRegisterUseCase = get(),
            loginByUserPassUseCase = get(),
            logoutUseCase = get(),
            getCurrentUserUseCase = get(),
            editUserDetailsUseCase = get(),
            getUserWalletUseCase = get(),
            observeFavoritesUseCase = get(),
            latestOrderUseCase = get(),
            seLanguageUseCase = get(),
            observeLanguageUseCase = get(),
            getPrivacyPolicyUseCase = get(),
            getContactInfoUseCase = get()
        )
    }
}
