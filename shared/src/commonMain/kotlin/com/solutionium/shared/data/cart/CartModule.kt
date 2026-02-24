package com.solutionium.shared.data.cart

import org.koin.dsl.module


val cartRepoModule = module {
    single<CartRepository> { CartRepositoryImpl(get()) }
}
