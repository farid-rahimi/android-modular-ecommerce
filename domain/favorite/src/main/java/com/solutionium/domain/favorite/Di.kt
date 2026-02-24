package com.solutionium.domain.favorite

import com.solutionium.shared.data.favorite.getFavoriteDataModules
import com.solutionium.domain.favorite.impl.ObserveFavoritesUseCaseImpl
import com.solutionium.domain.favorite.impl.SyncFavoriteUseCaseImpl
import com.solutionium.domain.favorite.impl.ToggleFavoriteUseCaseImpl
import org.koin.dsl.module

fun getFavoriteDomainModules() = setOf(favoriteDomainModule) + getFavoriteDataModules()


val favoriteDomainModule = module {
    factory<ToggleFavoriteUseCase> { ToggleFavoriteUseCaseImpl(get()) }
    factory<SyncFavoriteUseCase> { SyncFavoriteUseCaseImpl(get()) }
    factory<ObserveFavoritesUseCase> { ObserveFavoritesUseCaseImpl(get(), get()) }
}
