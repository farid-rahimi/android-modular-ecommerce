package com.solutionium.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.StoryItem
import com.solutionium.domain.config.GetStoriesUseCase

class GetStoriesUseCaseImpl(
    private val configRepository: AppConfigRepository
) : GetStoriesUseCase {
    override suspend fun invoke(): List<StoryItem> =
        when (val result = configRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.stories
            }

            is Result.Failure -> {
                emptyList()
            }
        }
}