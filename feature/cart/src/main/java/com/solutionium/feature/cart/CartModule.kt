package com.solutionium.feature.cart

import com.solutionium.domain.cart.getCartDomainModules
import com.solutionium.domain.config.getConfigDomainModules
import com.solutionium.domain.user.getUserDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getCartFeatureModules() = getCartDomainModules() + setOf(cartFeatureModule) + getUserDomainModules() + getCartDomainModules() + getConfigDomainModules()

val cartFeatureModule = module {
    viewModel {
        CartViewModel(
            updateCartItemUseCase = get(),
            observeCartUseCase = get(),
            clearCartUseCase = get(),
            validateCartUseCase = get(),
            confirmValidation = get(),
            paymentMethodDiscountUseCase = get(),
            checkLoginUserUseCase = get()
        )
    }
}
