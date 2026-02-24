package com.solutionium.shared.domain.favorite

interface SyncFavoriteUseCase {

    suspend operator fun invoke()
}