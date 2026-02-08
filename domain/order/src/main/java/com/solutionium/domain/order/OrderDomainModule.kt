package com.solutionium.domain.order

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class OrderDomainModule {

    @Binds
    abstract fun bindGetOrderListPagingUseCase(
        impl: GetOrderListPagingUseCaseImpl
    ): GetOrderListPagingUseCase

    @Binds
    abstract fun bindGetLatestOrderUseCase(
        impl: GetLatestOrderUseCaseImpl
    ): GetLatestOrderUseCase
}