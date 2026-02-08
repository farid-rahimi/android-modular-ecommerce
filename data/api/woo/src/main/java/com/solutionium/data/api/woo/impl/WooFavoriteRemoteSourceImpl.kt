package com.solutionium.data.api.woo.impl

import com.solutionium.data.api.woo.WooFavoriteRemoteSource
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.network.services.UserService
import javax.inject.Inject

class WooFavoriteRemoteSourceImpl @Inject constructor(

    private val apiService: UserService

) : WooFavoriteRemoteSource {
    override suspend fun getFavorites(): Result<List<Int>, GeneralError> {
        return Result.Success(emptyList()) // Placeholder
    }

    override suspend fun addFavorite(productId: Int): Result<Boolean, GeneralError> {
        return Result.Success(true) // Placeholder
    }

    override suspend fun removeFavorite(productId: Int): Result<Boolean, GeneralError> {
        return Result.Success(true) // Placeholder
    }
}