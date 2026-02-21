package com.solutionium.shared.data.api.woo

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result


interface WooFavoriteRemoteSource {

    suspend fun getFavorites(): Result<List<Int>, GeneralError>

    suspend fun addFavorite(productId: Int): Result<Boolean, GeneralError>

    suspend fun removeFavorite(productId: Int): Result<Boolean, GeneralError>

}