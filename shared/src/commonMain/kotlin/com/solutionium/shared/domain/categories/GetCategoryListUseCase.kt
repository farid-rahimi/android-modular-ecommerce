package com.solutionium.shared.domain.categories

import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetCategoryListUseCase {

    suspend operator fun invoke(): Flow<Result<List<Category>, GeneralError>>
}