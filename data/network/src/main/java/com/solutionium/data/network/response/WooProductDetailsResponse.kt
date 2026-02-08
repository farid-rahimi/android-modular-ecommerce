package com.solutionium.data.network.response

import com.solutionium.data.network.common.MetaDatum
import kotlinx.serialization.*
import kotlinx.serialization.json.*

typealias WooProductListResponse = List<WooProductDetailsResponse>

@Serializable
data class WooProductDetailsResponse (
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    val permalink: String? = null,

    @SerialName("date_created")
    val dateCreated: String? = null,

    @SerialName("date_created_gmt")
    val dateCreatedGmt: String? = null,

    @SerialName("date_modified")
    val dateModified: String? = null,

    @SerialName("date_modified_gmt")
    val dateModifiedGmt: String? = null,

    val type: String? = null,
    val status: String? = null,
    val featured: Boolean? = null,

    @SerialName("catalog_visibility")
    val catalogVisibility: String? = null,

    val description: String? = null,

    @SerialName("short_description")
    val shortDescription: String? = null,

    val sku: String? = null,
    val price: String? = null,

    @SerialName("regular_price")
    val regularPrice: String? = null,

    @SerialName("sale_price")
    val salePrice: String? = null,

    @SerialName("date_on_sale_from")
    val dateOnSaleFrom: String? = null,

    @SerialName("date_on_sale_from_gmt")
    val dateOnSaleFromGmt: String? = null,

    @SerialName("date_on_sale_to")
    val dateOnSaleTo: String? = null,

    @SerialName("date_on_sale_to_gmt")
    val dateOnSaleToGmt: String? = null,

    @SerialName("on_sale")
    val onSale: Boolean? = null,

    val purchasable: Boolean? = null,

    @SerialName("total_sales")
    val totalSales: Int? = null,

    val virtual: Boolean? = null,
    val downloadable: Boolean? = null,
    //val downloads: JsonArray? = null,


    @SerialName("external_url")
    val externalURL: String? = null,

    @SerialName("button_text")
    val buttonText: String? = null,

    @SerialName("tax_status")
    val taxStatus: String? = null,

    @SerialName("tax_class")
    val taxClass: String? = null,

    @SerialName("manage_stock")
    val manageStock: Boolean? = null,

    @SerialName("stock_quantity")
    val stockQuantity: Int? = null,

    val backorders: String? = null,

    @SerialName("backorders_allowed")
    val backordersAllowed: Boolean? = null,

    val backordered: Boolean? = null,

    @SerialName("low_stock_amount")
    val lowStockAmount: JsonElement? = null,

    @SerialName("sold_individually")
    val soldIndividually: Boolean? = null,

    val weight: String? = null,
    val dimensions: Dimensions? = null,

    @SerialName("shipping_required")
    val shippingRequired: Boolean? = null,

    @SerialName("shipping_taxable")
    val shippingTaxable: Boolean? = null,

    @SerialName("shipping_class")
    val shippingClass: String? = null,

    @SerialName("shipping_class_id")
    val shippingClassID: Int? = null,

    @SerialName("reviews_allowed")
    val reviewsAllowed: Boolean? = null,

    @SerialName("average_rating")
    val averageRating: String? = null,

    @SerialName("rating_count")
    val ratingCount: Int? = null,

    @SerialName("related_ids")
    val relatedIDS: List<Int>? = null,

    @SerialName("upsell_ids")
    val upsellIDS: List<Int>? = null,

    @SerialName("cross_sell_ids")
    val crossSellIDS: List<Int>? = null,

    @SerialName("parent_id")
    val parentID: Int? = null,

    @SerialName("purchase_note")
    val purchaseNote: String? = null,

    val categories: List<WooCategory>? = null,
    val brands: List<WooCategory>? = null,
    val tags: List<WooCategory>? = null,
    val images: List<Image>? = null,
    val attributes: List<Attribute>? = null,

    @SerialName("default_attributes")
    val defaultAttributes: List<WooVariationAttribute>? = emptyList(),

    val variations: List<Int>? = null,

    @SerialName("grouped_products")
    val groupedProducts: List<Int>?  = null,

    @SerialName("menu_order")
    val menuOrder: Long? = null,

    @SerialName("price_html")
    val priceHTML: String? = null,

    @SerialName("meta_data")
    val metaData: List<MetaDatum>? = null,

    @SerialName("stock_status")
    val stockStatus: String? = null,

    @SerialName("has_options")
    val hasOptions: Boolean? = null,

    @SerialName("post_password")
    val postPassword: String? = null,

    @SerialName("global_unique_id")
    val globalUniqueID: String? = null,

    )

@Serializable
data class Attribute (
    val id: Int? = null,
    val name: String? = null,
    val slug: String? = null,
    val position: Int? = null,
    val visible: Boolean? = null,
    val variation: Boolean? = null,
    val options: List<String>? = null
)



@Serializable
data class WooCategory (
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null
)

@Serializable
data class Dimensions (
    val length: String? = null,
    val width: String? = null,
    val height: String? = null
)








