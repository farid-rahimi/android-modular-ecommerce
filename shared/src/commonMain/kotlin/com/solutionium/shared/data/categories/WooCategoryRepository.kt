package com.solutionium.shared.data.categories

import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result

interface WooCategoryRepository {

    suspend fun getCategoryList(): Result<List<Category>, GeneralError>


}