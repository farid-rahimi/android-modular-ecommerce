package com.solutionium.data.woo.user

import com.solutionium.shared.data.database.entity.StoryViewEntity
import com.solutionium.shared.data.model.ViewedStory

fun StoryViewEntity.toModel() = ViewedStory(
    storyId = storyId,
    viewedAt = viewedAt
)