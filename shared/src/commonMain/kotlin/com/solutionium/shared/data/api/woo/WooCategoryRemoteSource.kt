package com.solutionium.shared.data.api.woo

import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result


interface WooCategoryRemoteSource {

    suspend fun getCategory(categoryId: Int): Result<Category, GeneralError>

    suspend fun getCategoryList(queries: Map<String, String>): Result<List<Category>, GeneralError>

}