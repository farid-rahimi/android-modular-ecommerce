package com.solutionium.domain.favorite

interface SyncFavoriteUseCase {

    suspend operator fun invoke()
}