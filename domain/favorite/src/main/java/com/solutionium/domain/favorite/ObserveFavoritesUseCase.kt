package com.solutionium.domain.favorite

import kotlinx.coroutines.flow.Flow

interface ObserveFavoritesUseCase {

    operator fun invoke(): Flow<List<Int>>

    suspend fun getSnapshot(): Set<Int>

}