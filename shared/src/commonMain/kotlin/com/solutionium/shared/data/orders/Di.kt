package com.solutionium.shared.data.orders

import org.koin.dsl.module

val orderDataModule = module {
    single<OrderRepository> { OrderRepositoryImpl(get(), get()) }
}
