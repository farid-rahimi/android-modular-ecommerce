package com.solutionium.shared.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_view")
data class StoryViewEntity(
    @PrimaryKey
    val storyId: Int,
    val viewedAt: Long = System.currentTimeMillis()
)
