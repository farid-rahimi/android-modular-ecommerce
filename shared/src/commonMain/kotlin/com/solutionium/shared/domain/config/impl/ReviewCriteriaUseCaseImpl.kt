package com.solutionium.shared.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.domain.config.ReviewCriteriaUseCase

class ReviewCriteriaUseCaseImpl(
    private val appConfigRepository: AppConfigRepository
) : ReviewCriteriaUseCase {
    override suspend fun invoke(catIds: List<Int>): List<String> =
        when (val result = appConfigRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.reviewCriteria.find { it.catID in catIds }?.criteria ?: emptyList()
            }

            is Result.Failure -> {
                emptyList()
            }
        }

}