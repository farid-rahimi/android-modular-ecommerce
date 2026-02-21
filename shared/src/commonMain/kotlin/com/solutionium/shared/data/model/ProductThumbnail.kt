package com.solutionium.shared.data.model

enum class ProductCatType(val value: String) { PERFUME("perfume"), SHOES("shoes"), OTHER("other") }
enum class ProductVarType(val value: String) { SIMPLE("simple"), VARIABLE("variable") }
data class ProductThumbnail(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val rating: Double,
    val ratingCount: Int,
    val shortDescription: String,
    val categories: List<SimpleTerm>,
    val brands: List<SimpleTerm>,
    val tags: List<SimpleTerm>,
    val onSale: Boolean = false,

    val salePrice: Double? = null,
    val regularPrice: Double? = null,
    val varType: ProductVarType = ProductVarType.SIMPLE,
    val type: ProductCatType = ProductCatType.OTHER,
    val manageStock: Boolean,
    val stock: Int,
    val stockStatus: String,

    val decants: List<String>? = null, // Example decant sizes

    // Type-specific attributes
    // Perfume
    val scentGroup: List<String>? = null,
    val volume: String? = null, // e.g., "100ml", "50ml"

    // Shoes
    val sizingRange: String? = null, // e.g., "US 7-12"
    val availableColorsHex: List<String>? = null, // List of hex color codes

    // For simple add to cart
    val hasSimpleAddToCart: Boolean = true, // To show/hide plus icon
    val shippingClass: String? = null,

    val appOffer: Double = 0.0

) {

//    init {
//        if (appOffer > 0 && !onSale) {
//            val discountedPrice = price * (1 - appOffer / 100)
//            //product = product.copy(
//                regularPrice = price
//                price = discountedPrice
//                salePrice = discountedPrice
//                onSale = true
//            //)
//        }
//    }

    val isInStock = (stockStatus == "instock")

    fun features(): List<FeatureType> {

        val features = mutableListOf<FeatureType>()

        if (shippingClass == "free-shipping")
            features.add(FeatureType.FREE_SHIPPING)

        if (tags.any { it.slug == "fast_selling" })
            features.add(FeatureType.FAST_SELLING)

        if (tags.any { it.slug == "authentic" })
            features.add(FeatureType.AUTHENTIC)

        if (tags.any { it.slug == "size_exchange" })
            features.add(FeatureType.SIZE_EXCHANGE)

        if (tags.any { it.slug == "high_quality" })
            features.add(FeatureType.HIGH_QUALITY)

        if (tags.any { it.slug == "team_choice" })
            features.add(FeatureType.TEAM_CHOICE)

        return features
    }

    fun isOnSale(): Boolean {
        return onSale || appOffer > 0
    }

    fun salesPercentage(): Int {

        if (appOffer > 0)
            return appOffer.toInt()

        return if (salePrice != null && regularPrice != null && regularPrice > salePrice) {
            (((regularPrice - salePrice) / regularPrice) * 100).toInt()
        } else {
            0
        }
    }
}

enum class ProductListType(
    val queries: Map<String, String>
) {
    New (queries = mapOf("new" to "true")),
    Popular (queries = mapOf("new" to "true")),
    Features (queries = mapOf("featured" to "true")),
    OnSale (queries = mapOf("on_sale" to "true")),
    Offers (queries = mapOf("tag" to "5617")),
}



enum class FeatureType {
    FREE_SHIPPING,
    FAST_SELLING,
    AUTHENTIC,
    SIZE_EXCHANGE,
    HIGH_QUALITY,
    TEAM_CHOICE
}

