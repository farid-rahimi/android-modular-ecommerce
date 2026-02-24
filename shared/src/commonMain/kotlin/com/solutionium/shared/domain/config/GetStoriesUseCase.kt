package com.solutionium.shared.domain.config

import com.solutionium.shared.data.model.StoryItem

interface GetStoriesUseCase {

    suspend operator fun invoke(): List<StoryItem>

}