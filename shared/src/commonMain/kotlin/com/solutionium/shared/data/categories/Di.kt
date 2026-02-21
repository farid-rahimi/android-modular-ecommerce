package com.solutionium.shared.data.categories

import org.koin.dsl.module

val categoryDataModule = module {
    single<WooCategoryRepository> { WooCategoryRepositoryImpl(get()) }
}
