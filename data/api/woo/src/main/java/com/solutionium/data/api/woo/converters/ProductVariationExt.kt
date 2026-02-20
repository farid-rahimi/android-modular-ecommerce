package com.solutionium.data.api.woo.converters

import com.solutionium.data.model.ProductVariation
import com.solutionium.data.model.VariationAttribute
import com.solutionium.shared.data.network.response.WooProductVariationResponse
import com.solutionium.shared.data.network.response.WooVariationAttribute

fun WooProductVariationResponse.toModel(): ProductVariation {
    return ProductVariation(
        id = id,
        price = price.toDouble() ,
        regularPrice = if (onSale) regularPrice?.toDouble() else null,
        salePrice = if (salePrice.isNullOrBlank()) null else salePrice?.toDouble(),
        onSale = onSale,
        manageStock = manageStock,
        stock = stockQuantity ?: 0,
        stockStatus = stockStatus,
        attributes = attributes.map { it.toModel() },
        image = image.src,
    )
}

fun WooVariationAttribute.toModel(): VariationAttribute =
    VariationAttribute(
        id = id,
        name = name,
        slug = slug,
        option = option
    )