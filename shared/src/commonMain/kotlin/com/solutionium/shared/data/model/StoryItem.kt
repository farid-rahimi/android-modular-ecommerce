package com.solutionium.shared.data.model

data class StoryItem(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val mediaUrl: String,
    val link: Link? = null,
    // To track if the story has been viewed
    var isViewed: Boolean = false
)