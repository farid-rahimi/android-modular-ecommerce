package com.solutionium.data.woo.categories

import com.solutionium.data.api.woo.WooCategoryRemoteSource
import com.solutionium.data.model.Category
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result

class WooCategoryRepositoryImpl(
    private val wooCategoryRemoteSource : WooCategoryRemoteSource
): WooCategoryRepository {
    override suspend fun getCategoryList(): Result<List<Category>, GeneralError> =
        wooCategoryRemoteSource.getCategoryList(emptyMap())
}