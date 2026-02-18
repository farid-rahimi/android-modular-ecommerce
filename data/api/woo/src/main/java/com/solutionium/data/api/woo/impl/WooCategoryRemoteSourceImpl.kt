package com.solutionium.data.api.woo.impl

import com.solutionium.data.api.woo.WooCategoryRemoteSource
import com.solutionium.data.api.woo.converters.toCategory
import com.solutionium.data.api.woo.handleNetworkResponse
import com.solutionium.data.model.Category
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.network.clients.WooCategoryClient

class WooCategoryRemoteSourceImpl(
    private val categoryApi: WooCategoryClient
) : WooCategoryRemoteSource {
    override suspend fun getCategory(categoryId: Int): Result<Category, GeneralError> =
        handleNetworkResponse(
            networkCall = { categoryApi.getCategoryDetails(categoryId = categoryId) },
            mapper = { response -> response.toCategory() }
        )


    override suspend fun getCategoryList(queries: Map<String, String>): Result<List<Category>, GeneralError> =
        handleNetworkResponse(
            networkCall = { categoryApi.getCategoryList(queries) },
            mapper = { responseList -> responseList.map { it.toCategory() } }
        )

}
