package com.solutionium.data.woo.checkout

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CheckoutModule {

    @Binds
    abstract fun bindCheckoutRepository(
        impl: CheckoutRepositoryImpl
    ): CheckoutRepository

    @Binds
    abstract fun bindCouponRepository(
        impl: CouponRepositoryImpl
    ): CouponRepository
}