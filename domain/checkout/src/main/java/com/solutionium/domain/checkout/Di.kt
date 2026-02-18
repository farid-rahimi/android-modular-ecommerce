package com.solutionium.domain.checkout

import com.solutionium.data.woo.checkout.checkoutDataModule
import com.solutionium.domain.checkout.impl.ApplyCouponUseCaseImpl
import com.solutionium.domain.checkout.impl.CreateOrderUseCaseImpl
import com.solutionium.domain.checkout.impl.GetOrderStatusUseCaseImpl
import com.solutionium.domain.checkout.impl.GetPaymentGatewaysUseCaseImpl
import com.solutionium.domain.checkout.impl.GetShippingMethodsUseCaseImpl
import org.koin.dsl.module

fun getCheckoutDomainModules() = setOf(checkoutDomainModule, checkoutDataModule)

val checkoutDomainModule = module {
    factory<GetShippingMethodsUseCase> { GetShippingMethodsUseCaseImpl(get()) }
    factory<GetPaymentGatewaysUseCase> { GetPaymentGatewaysUseCaseImpl(get()) }
    factory<CreateOrderUseCase> { CreateOrderUseCaseImpl(get()) }
    factory<GetOrderStatusUseCase> { GetOrderStatusUseCaseImpl(get()) }
    factory<ApplyCouponUseCase> { ApplyCouponUseCaseImpl(get()) }
}
