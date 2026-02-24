package com.solutionium.shared.data.checkout

import org.koin.dsl.module

val checkoutDataModule = module {
    single<CheckoutRepository> { CheckoutRepositoryImpl(get(), get()) }
    single<CouponRepository> { CouponRepositoryImpl(get()) }
}
