package com.solutionium.data.favorite

import com.solutionium.data.api.woo.apiModule
import com.solutionium.data.api.woo.getApiModule
import com.solutionium.data.database.databaseModule
import org.koin.dsl.module

fun getFavoriteDataModules() = setOf(favoriteDataModule, databaseModule) + getApiModule()

val favoriteDataModule = module {
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }
}
