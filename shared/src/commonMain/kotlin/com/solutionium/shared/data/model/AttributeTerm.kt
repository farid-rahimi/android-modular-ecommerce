package com.solutionium.shared.data.model

data class AttributeTerm(
    override val id: Int,
    override val name: String,
    override val slug: String,
    val description: String,
    override val menuOrder: Int,
    override val count: Int,
    override val imageUrl: String? = null
) : DisplayableTerm

enum class AttributeTermsListType(
    val attributeId: Int = 0,
    val queries: Map<String, String> = emptyMap()
) {
    All(queries = mapOf("per_page" to "100")),
    Genders(attributeId = 9),
    AllScentGroup(attributeId = 7, queries = mapOf("orderby" to "count", "order" to "desc", "per_page" to "99")),
    TopScentGroup(attributeId = 7, queries = mapOf("orderby" to "count", "order" to "desc", "per_page" to "6")),
    Seasons(attributeId = 21)
}

