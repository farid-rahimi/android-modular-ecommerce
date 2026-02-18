package com.solutionium.data.woo.orders

import org.koin.dsl.module

val orderDataModule = module {
    single<OrderRepository> { OrderRepositoryImpl(get(), get()) }
}
