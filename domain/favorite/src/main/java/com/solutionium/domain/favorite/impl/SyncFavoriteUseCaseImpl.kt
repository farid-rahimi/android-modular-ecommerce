package com.solutionium.domain.favorite.impl

import com.solutionium.data.favorite.FavoriteRepository
import com.solutionium.domain.favorite.SyncFavoriteUseCase
import javax.inject.Inject

class SyncFavoriteUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : SyncFavoriteUseCase {
    override suspend fun invoke() =
        favoriteRepository.syncFavorites()
}