package com.solutionium.domain.favorite.impl

import com.solutionium.data.favorite.FavoriteRepository
import com.solutionium.domain.favorite.ToggleFavoriteUseCase
import javax.inject.Inject

class ToggleFavoriteUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ToggleFavoriteUseCase {
    override suspend fun invoke(productId: Int, isCurrentlyFavorite: Boolean) =
        favoriteRepository.toggleFavoriteStatus(productId, isCurrentlyFavorite)

}