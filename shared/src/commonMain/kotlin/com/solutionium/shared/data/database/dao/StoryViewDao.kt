package com.solutionium.shared.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solutionium.shared.data.database.entity.StoryViewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryViewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStoryView(story: StoryViewEntity)

    /**
     * Gets all viewed story IDs as a Flow. The list will automatically update
     * when the data changes.
     */
    @Query("SELECT * FROM story_view")
    fun getAllStoryViewIds(): Flow<List<StoryViewEntity>>

    /**
     * Checks if a specific story ID exists in the table.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM story_view WHERE storyId = :storyId)")
    suspend fun hasBeenViewed(storyId: Int): Boolean

}