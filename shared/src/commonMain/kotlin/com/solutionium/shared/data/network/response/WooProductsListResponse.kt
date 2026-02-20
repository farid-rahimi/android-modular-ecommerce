package com.solutionium.shared.data.network.response


import kotlinx.serialization.*

@Serializable
data class WooProductsListResponse (
    val page: Int? = null,

    @SerialName("per_page")
    val perPage: Int? = null,

    val count: Int? = null,
    val products: List<WooProduct>? = null
)

@Serializable
data class WooProduct (
    val id: Int? = null,
    val title: String? = null,
    val thumbnail: String? = null,
    val rating: Float? = null,
    val ratingCount: Int? = null,
    val brands: List<WooTerm>? = null,
    val categories: List<WooTerm>? = null,
    val tags: List<WooTerm>? = null,
    val attributes: List<Attribute>? = null,

    @SerialName("product_type")
    val productType: String? = null,

    @SerialName("shipping_class")
    val shippingClass: WooTerm? = null,

    val price: String? = null,

    @SerialName("regular_price")
    val regularPrice: String? = null,

    @SerialName("sale_price")
    val salePrice: String? = null,

    @SerialName("on_sale")
    val onSale: Boolean? = null,

    val manageStock: Boolean? = null,

    @SerialName("stock_status")
    val stockStatus: String? = null,

    @SerialName("stock_qty")
    val stockQty: Int? = null,

    @SerialName("_app_offer")
    val appOffer: String? = null
)


@Serializable
data class WooTerm (
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null
)


