package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.Result
import com.solutionium.domain.config.ReviewCriteriaUseCase
import javax.inject.Inject

class ReviewCriteriaUseCaseImpl @Inject constructor(
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