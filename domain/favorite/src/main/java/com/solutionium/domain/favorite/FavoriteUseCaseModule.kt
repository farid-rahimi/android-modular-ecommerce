package com.solutionium.domain.favorite


import com.solutionium.domain.favorite.impl.ObserveFavoritesUseCaseImpl
import com.solutionium.domain.favorite.impl.SyncFavoriteUseCaseImpl
import com.solutionium.domain.favorite.impl.ToggleFavoriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteUseCaseModule {

    @Binds
    abstract fun bindToggleFavoriteUseCase(
        impl: ToggleFavoriteUseCaseImpl
    ): ToggleFavoriteUseCase

    @Binds
    abstract fun bindSyncFavoriteUseCase(
        impl: SyncFavoriteUseCaseImpl
    ): SyncFavoriteUseCase

    @Binds
    abstract fun bindIsFavoriteUseCase(
        impl: ObserveFavoritesUseCaseImpl
    ): ObserveFavoritesUseCase
}