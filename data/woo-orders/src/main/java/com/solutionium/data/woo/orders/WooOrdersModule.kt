package com.solutionium.data.woo.orders

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WooOrdersModule {


    @Binds
    abstract fun bindOrderRepositoryImpl(
        impl: OrderRepositoryImpl
    ): OrderRepository


}