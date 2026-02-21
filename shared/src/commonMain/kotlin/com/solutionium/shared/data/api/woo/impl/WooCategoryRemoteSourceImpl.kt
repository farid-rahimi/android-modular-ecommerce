package com.solutionium.shared.data.api.woo.impl

import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.api.woo.WooCategoryRemoteSource
import com.solutionium.shared.data.api.woo.converters.toCategory
import com.solutionium.shared.data.api.woo.handleNetworkResponse
import com.solutionium.shared.data.network.clients.WooCategoryClient
import com.solutionium.shared.data.model.Result


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
