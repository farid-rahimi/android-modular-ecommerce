package com.solutionium.shared.domain.favorite.impl

import com.solutionium.shared.data.favorite.FavoriteRepository
import com.solutionium.shared.domain.favorite.ToggleFavoriteUseCase

class ToggleFavoriteUseCaseImpl(
    private val favoriteRepository: FavoriteRepository
) : ToggleFavoriteUseCase {
    override suspend fun invoke(productId: Int, isCurrentlyFavorite: Boolean) =
        favoriteRepository.toggleFavoriteStatus(productId, isCurrentlyFavorite)

}