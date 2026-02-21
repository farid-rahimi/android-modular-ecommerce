package com.solutionium.shared.data.categories

import com.solutionium.shared.data.api.woo.WooCategoryRemoteSource
import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result

class WooCategoryRepositoryImpl(
    private val wooCategoryRemoteSource : WooCategoryRemoteSource
): WooCategoryRepository {
    override suspend fun getCategoryList(): Result<List<Category>, GeneralError> =
        wooCategoryRemoteSource.getCategoryList(emptyMap())
}