package com.solutionium.shared.domain.categories

import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.categories.WooCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoryListUseCaseImpl(
    private val wooCategoryRepository: WooCategoryRepository
): GetCategoryListUseCase {
    override suspend fun invoke(): Flow<Result<List<Category>, GeneralError>> = flow{
        emit(wooCategoryRepository.getCategoryList())
    }

}
