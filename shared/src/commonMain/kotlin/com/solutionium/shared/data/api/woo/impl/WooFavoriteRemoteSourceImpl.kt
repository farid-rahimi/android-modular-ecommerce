package com.solutionium.shared.data.api.woo.impl

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.api.woo.WooFavoriteRemoteSource
import com.solutionium.shared.data.network.clients.UserClient
import com.solutionium.shared.data.model.Result


class WooFavoriteRemoteSourceImpl(

    private val apiService: UserClient

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