package com.solutionium.shared.domain.checkout

import com.solutionium.shared.data.checkout.checkoutDataModule
import com.solutionium.shared.domain.checkout.impl.GetOrderStatusUseCaseImpl
import com.solutionium.shared.domain.checkout.impl.ApplyCouponUseCaseImpl
import com.solutionium.shared.domain.checkout.impl.CreateOrderUseCaseImpl
import com.solutionium.shared.domain.checkout.impl.GetPaymentGatewaysUseCaseImpl
import com.solutionium.shared.domain.checkout.impl.GetShippingMethodsUseCaseImpl
import org.koin.dsl.module

fun getCheckoutDomainModules() = setOf(checkoutDomainModule, checkoutDataModule)

val checkoutDomainModule = module {
    factory<GetShippingMethodsUseCase> { GetShippingMethodsUseCaseImpl(get()) }
    factory<GetPaymentGatewaysUseCase> { GetPaymentGatewaysUseCaseImpl(get()) }
    factory<CreateOrderUseCase> { CreateOrderUseCaseImpl(get()) }
    factory<GetOrderStatusUseCase> { GetOrderStatusUseCaseImpl(get()) }
    factory<ApplyCouponUseCase> { ApplyCouponUseCaseImpl(get()) }
}
