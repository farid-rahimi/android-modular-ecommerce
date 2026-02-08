package com.solutionium.domain.woo.categories

import com.solutionium.data.model.Category
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetCategoryListUseCase {

    suspend operator fun invoke(): Flow<Result<List<Category>, GeneralError>>
}