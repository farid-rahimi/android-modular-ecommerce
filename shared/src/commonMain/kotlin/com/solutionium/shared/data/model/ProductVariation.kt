package com.solutionium.shared.data.model

data class ProductVariation(
    val id: Int,
    val price: Double,
    val salePrice: Double? = null,
    val regularPrice: Double? = null,
    val onSale: Boolean = false,
    val manageStock: Boolean,
    val stock: Int,
    val stockStatus: String,
    val image: String?,
    val attributes: List<VariationAttribute>,
) {
    val lowInStock: Boolean
        get() = manageStock && stock in 1..3
}

data class VariationAttribute(
    val id: Int,
    val name: String,
    val slug: String? = null,
    val option: String
)

