package com.solutionium.shared.data.model

sealed interface DisplayableTerm {
    val id: Int
    val name: String
    val slug: String
    val menuOrder: Int?
    val count: Int
    val imageUrl: String?
}
