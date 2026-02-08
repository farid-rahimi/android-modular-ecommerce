package com.solutionium.data.api.woo

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result

interface WooFavoriteRemoteSource {

    suspend fun getFavorites(): Result<List<Int>, GeneralError>

    suspend fun addFavorite(productId: Int): Result<Boolean, GeneralError>

    suspend fun removeFavorite(productId: Int): Result<Boolean, GeneralError>

}