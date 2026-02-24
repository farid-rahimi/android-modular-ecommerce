package com.solutionium.shared.domain.favorite

interface ToggleFavoriteUseCase {

    suspend operator fun invoke(productId: Int, isCurrentlyFavorite: Boolean)

}