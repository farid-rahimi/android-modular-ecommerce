package com.solutionium.data.woo.categories

import com.solutionium.data.model.Category
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result

interface WooCategoryRepository {

    suspend fun getCategoryList(): Result<List<Category>, GeneralError>


}