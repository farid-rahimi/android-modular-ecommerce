package com.solutionium.shared.data.products

import org.koin.dsl.module

val productsDataModule = module {
    single<WooProductRepository> { WooProductRepositoryImpl(get()) }
}
