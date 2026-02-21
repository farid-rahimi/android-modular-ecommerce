package com.solutionium.shared.data.model

import kotlin.math.round
import kotlin.math.roundToInt

data class Decant(
    val size: String, // e.g., "5ml", "10ml"
    val price: Double,
    val regularPrice: Double?
)



data class ProductDetail(
    val id: Int, //
    val name: String, //
    val permalink: String, //
    val price: Double, //
    val salePrice: Double? = null, //
    val regularPrice: Double? = null, //
    val onSale: Boolean = false, //
    val priceHtml: String,
    val imageUrls: List<String>, //
    val rating: Double, //
    val ratingCount: Int, //
    val varType: ProductVarType = ProductVarType.SIMPLE, //
    val type: ProductCatType = ProductCatType.OTHER, //
    val manageStock: Boolean, //
    val stock: Int, //
    val stockStatus: String, //
    val shortDescription: String, //
    val purchasable: Boolean = true,
    val description: String, //
    val inspiration: String? = "",
    val categories: List<SimpleTerm>, //
    val brands: List<SimpleTerm>, //
    val tags: List<SimpleTerm>, //
    val attributes: List<ProductAttribute>, //
    val defaultAttributes: List<VariationAttribute>,
    val concentration: String? = "",
    val decantsStr: List<String>? = null, // Example decant sizes

    // ▼▼▼ UPDATED THIS LINE ▼▼▼
    //val decants: List<Decant>? = null, // Now a list of Decant objects


    // Type-specific attributes
    // Perfume
    val scentGroup: List<String>? = null,
    val volume: String? = null, // e.g., "100ml", "50ml"

    // Shoes
    val sizingRange: String? = null, // e.g., "US 7-12"
    // For simple add to cart
    val hasSimpleAddToCart: Boolean = true, // To show/hide plus icon
    val genderAffinity: String? = "",
    val usageSuggestion: String? = "",
    val variations: List<Int>? = emptyList(),
    val shippingClass: String? = null,
    val appOffer: Double = 0.0

) {
    val isInStock: Boolean
        get() = stockStatus.equals("instock", ignoreCase = true) || ( manageStock && stock > 0 )

    val isAvailable: Boolean
        get() = stockStatus != "outofstock"


    val lowInStock: Boolean
        get() = manageStock && stock in 1..3

    // $dec_price = round($dec_vol/$volumeval * $base_price * 1.3 + 40000, -4);
    val decants: List<Decant>
        get() = decantsStr?.map { sizeStr ->
            val sizeValue = getDecantBySize(sizeStr)
            val fullBottleVolume = volume?.removeSuffix("میل")?.trim()?.toDoubleOrNull()
            val price = ((sizeValue ?: 5.0)/(fullBottleVolume ?: 100.0) * 1.3 * price + 40000).roundToNearestTenThousand()
            val regularPrice = if (onSale) {
                ((sizeValue ?: 5.0)/(fullBottleVolume ?: 100.0) * 1.3 * (regularPrice ?: price) + 40000).roundToNearestTenThousand()
            } else null
            Decant(sizeStr, price, regularPrice)
        } ?: emptyList()


}

fun getDecantBySize(size: String): Double? = size.removeSuffix("میل").trim().toDoubleOrNull()



data class ProductAttribute(
    val id: Int,
    val name: String,
    val slug: String,
    val position: Int,
    val visible: Boolean = false,
    val variation: Boolean = false, //Define if the attribute can be used as variation. Default is false.
    val options: List<String>
)

fun Double.roundToNearestTenThousand(): Double {
    return round(this / 10000.0) * 10000.0
}