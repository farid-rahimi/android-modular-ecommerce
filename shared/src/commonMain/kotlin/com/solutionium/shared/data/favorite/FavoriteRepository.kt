package com.solutionium.shared.data.favorite

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavoriteIds(): Flow<List<Int>>
    suspend fun getFavoriteIds(): Set<Int>
    suspend fun toggleFavoriteStatus(productId: Int, isCurrentlyFavorite: Boolean)
    suspend fun syncFavorites()
}