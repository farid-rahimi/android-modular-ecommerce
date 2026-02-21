package com.solutionium.data.woo.user

import com.solutionium.shared.data.model.ViewedStory
import kotlinx.coroutines.flow.Flow

interface StoryViewRepository {

    fun getViewedStoryIds(): Flow<List<ViewedStory>>
    suspend fun addViewedStoryId(storyId: Int)

}