package com.solutionium.feature.checkout

import com.solutionium.shared.domain.cart.getCartDomainModules
import com.solutionium.domain.checkout.getCheckoutDomainModules
import com.solutionium.domain.config.getConfigDomainModules
import com.solutionium.domain.user.getUserDomainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getCheckoutModules() = setOf(checkoutModule) + getCartDomainModules() + getCheckoutDomainModules() + getUserDomainModules() + getConfigDomainModules()


val checkoutModule = module {
    viewModel {
        CheckoutViewModel(
            observeCartUseCase = get(),
            getShippingMethodsUseCase = get(),
            getForcedEnabledPayment = get(),
            getPaymentGatewaysUseCase = get(),
            loadAddressesUseCase = get(),
            applyCouponUseCase = get(),
            createOrderUseCase = get(),
            clearCartUseCase = get(),
            getOrderStatusUseCase = get(),
            paymentMethodDiscountUseCase = get(),
            getBACSDetails = get(),
            getUserWalletUseCase = get(),
            context = androidContext()
        )
    }
}
