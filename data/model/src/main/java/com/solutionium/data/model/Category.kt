package com.solutionium.data.model


const val PERFUME_CAT_ID = "1910"
const val SHOES_CAT_ID = "5437"

data class Category(
    override val id: Int,
    override val name: String,
    override val slug: String,
    val parent: Int,
    val description: String,
    val display: String,
    override val imageUrl: String,
    override val menuOrder: Int,
    override val count: Int,
) : DisplayableTerm


