package com.solutionium.domain.config

import com.solutionium.data.model.StoryItem

interface GetStoriesUseCase {

    suspend operator fun invoke(): List<StoryItem>

}