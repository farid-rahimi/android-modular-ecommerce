package com.solutionium.domain.checkout

import com.solutionium.domain.checkout.impl.CreateOrderUseCaseImpl
import com.solutionium.domain.checkout.impl.GetOrderStatusUseCaseImpl
import com.solutionium.domain.checkout.impl.GetPaymentGatewaysUseCaseImpl
import com.solutionium.domain.checkout.impl.GetShippingMethodsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CheckoutDomainModule {

    @Binds
    fun bindGetShippingMethodsUseCase(
        getShippingMethodsUseCaseImpl: GetShippingMethodsUseCaseImpl
    ): GetShippingMethodsUseCase

    @Binds
    fun bindGetPaymentGatewaysUseCase(
        getPaymentGatewaysUseCaseImpl: GetPaymentGatewaysUseCaseImpl
    ): GetPaymentGatewaysUseCase

    @Binds
    fun bindCreateOrderUseCase(
        createOrderUseCaseImpl: CreateOrderUseCaseImpl
    ): CreateOrderUseCase

    @Binds
    fun bindGetOrderStatusUseCase(
        getOrderStatusUseCaseImpl: GetOrderStatusUseCaseImpl
    ): GetOrderStatusUseCase

    @Binds
    fun bindApplyCouponUseCase(
        applyCouponUseCaseImpl: com.solutionium.domain.checkout.impl.ApplyCouponUseCaseImpl
    ): ApplyCouponUseCase
}