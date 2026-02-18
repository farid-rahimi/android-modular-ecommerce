package com.solutionium.data.woo.products

import org.koin.dsl.module

val productsDataModule = module {
    single<WooProductRepository> { WooProductRepositoryImpl(get(), get()) }
}
