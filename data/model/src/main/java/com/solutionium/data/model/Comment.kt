package com.solutionium.data.model

data class Comment(
    val id: Int,
    val author: String,
    val rating: Int, // 1 to 5
    val content: String,
    val date: String
)