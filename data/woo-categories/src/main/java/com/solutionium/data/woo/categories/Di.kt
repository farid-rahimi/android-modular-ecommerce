package com.solutionium.data.woo.categories

import org.koin.dsl.module

val categoryDataModule = module {
    single<WooCategoryRepository> { WooCategoryRepositoryImpl(get()) }
}
