package com.solutionium.shared.data.model

data class Brand(
    override val id: Int,
    override val name: String,
    override val slug: String,
    val parent: Int,
    val description: String,
    val display: String,
    override val menuOrder: Int,
    override val count: Int,
    override val imageUrl: String
) : DisplayableTerm

enum class BrandListType(
    val queries: Map<String, String>
) {
    All(queries = mapOf("exclude" to "5266,5270,5271,5273,5357,5276,5289,5288,5355", "per_page" to "100", "orderby" to "count", "order" to "desc"), ),
    Popular(queries = mapOf("orderby" to "count", "order" to "desc")),
    TopPerfumes(queries = mapOf("include" to "5259,5261,5330,5593,5260,5262,5263,5264,5265,5274")),
    TopShoes(queries = mapOf("include" to "5441,5442,5443,5444,5515")) //
}

