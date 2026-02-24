package com.solutionium.data.favorite

import com.solutionium.shared.data.api.woo.WooFavoriteRemoteSource
import com.solutionium.shared.data.database.dao.FavoriteDao
import com.solutionium.shared.data.model.Favorite
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao,
    private val remoteDataSource: WooFavoriteRemoteSource // Your Retrofit/Ktor service
) : FavoriteRepository {

    override fun observeFavoriteIds(): Flow<List<Int>> {

        // The UI always observes the local database for instant updates.
        return favoriteDao.observeFavoriteIds()//.map { it.toSet() }
    }

    override suspend fun getFavoriteIds(): Set<Int> {
        return favoriteDao.getAllFavorites().map { it.productId }.toSet()
    }

    override suspend fun toggleFavoriteStatus(productId: Int, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite) {
            favoriteDao.removeFavorite(productId)


        } else {
            favoriteDao.addFavorite(Favorite(productId).toEntity())

        }
    }

    override suspend fun syncFavorites() {
        // 1. Fetch the full list of favorite IDs from the API.
        when (val result = remoteDataSource.getFavorites()) {
            is Result.Success -> {
                val remoteFavoriteIds = result.data
                // 2. Overwrite the local database with the server's list.
                // This is a simple "server-is-truth" sync strategy.
                val favoriteEntities = remoteFavoriteIds.map { Favorite(productId = it).toEntity() }
                //localDataSource.replaceAllFavorites(favoriteEntities)
            }
            is Result.Failure -> {
                // Handle error (e.g., log it, show a message, etc.)
            }
        }


    }
}