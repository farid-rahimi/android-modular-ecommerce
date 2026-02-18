package com.solutionium.domain.user

import com.solutionium.data.model.ViewedStory
import com.solutionium.data.woo.user.StoryViewRepository
import kotlinx.coroutines.flow.Flow

interface GetAllStoryViewUseCase {
     operator fun invoke(): Flow<List<ViewedStory>>
}

internal class GetAllStoryViewUseCaseImpl(
    private val storyViewRepository: StoryViewRepository
) : GetAllStoryViewUseCase {
    override fun invoke(): Flow<List<ViewedStory>> =
        storyViewRepository.getViewedStoryIds()
}

interface AddStoryViewUseCase {
    suspend operator fun invoke(storyId: Int)
}

internal class AddStoryViewUseCaseImpl(
    private val storyViewRepository: StoryViewRepository
) : AddStoryViewUseCase {
    override suspend fun invoke(storyId: Int) {
        storyViewRepository.addViewedStoryId(storyId)
    }
}