package com.solutionium.data.woo.user

import com.solutionium.data.database.dao.StoryViewDao
import com.solutionium.data.database.entity.StoryViewEntity
import com.solutionium.data.model.ViewedStory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class StoryViewRepositoryImpl(
    private val storyViewDao: StoryViewDao
) : StoryViewRepository {
    override fun getViewedStoryIds(): Flow<List<ViewedStory>> =
        storyViewDao.getAllStoryViewIds().map { entities ->
            entities.map { it.toModel() }
        }

    override suspend fun addViewedStoryId(storyId: Int) {
        if (!storyViewDao.hasBeenViewed(storyId)) {
            storyViewDao.insertStoryView(StoryViewEntity(storyId = storyId))
        }
    }
}