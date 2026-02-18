package com.solutionium.domain.woo.categories

import com.solutionium.data.model.Category
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.woo.categories.WooCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoryListUseCaseImpl(
    private val wooCategoryRepository: WooCategoryRepository
): GetCategoryListUseCase {
    override suspend fun invoke(): Flow<Result<List<Category>, GeneralError>> = flow{
        emit(wooCategoryRepository.getCategoryList())
    }

}
