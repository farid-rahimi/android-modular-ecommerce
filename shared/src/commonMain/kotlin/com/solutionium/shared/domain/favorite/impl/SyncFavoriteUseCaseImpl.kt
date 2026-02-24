package com.solutionium.shared.domain.favorite.impl

import com.solutionium.shared.data.favorite.FavoriteRepository
import com.solutionium.shared.domain.favorite.SyncFavoriteUseCase

class SyncFavoriteUseCaseImpl(
    private val favoriteRepository: FavoriteRepository
) : SyncFavoriteUseCase {
    override suspend fun invoke() =
        favoriteRepository.syncFavorites()
}