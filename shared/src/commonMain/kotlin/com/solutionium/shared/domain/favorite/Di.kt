package com.solutionium.shared.domain.favorite

import com.solutionium.shared.data.favorite.getFavoriteDataModules
import com.solutionium.shared.domain.favorite.impl.SyncFavoriteUseCaseImpl
import com.solutionium.shared.domain.favorite.impl.ObserveFavoritesUseCaseImpl
import com.solutionium.shared.domain.favorite.impl.ToggleFavoriteUseCaseImpl
import org.koin.dsl.module

fun getFavoriteDomainModules() = setOf(favoriteDomainModule) + getFavoriteDataModules()


val favoriteDomainModule = module {
    factory<ToggleFavoriteUseCase> { ToggleFavoriteUseCaseImpl(get()) }
    factory<SyncFavoriteUseCase> { SyncFavoriteUseCaseImpl(get()) }
    factory<ObserveFavoritesUseCase> { ObserveFavoritesUseCaseImpl(get(), get()) }
}
