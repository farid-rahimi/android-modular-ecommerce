package com.solutionium.data.api.woo

import com.solutionium.data.model.Category
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result

interface WooCategoryRemoteSource {

    suspend fun getCategory(categoryId: Int): Result<Category, GeneralError>

    suspend fun getCategoryList(queries: Map<String, String>): Result<List<Category>, GeneralError>

}