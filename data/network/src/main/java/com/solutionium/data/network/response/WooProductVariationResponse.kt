package com.solutionium.data.network.response


import com.solutionium.data.network.common.MetaDatum
import kotlinx.serialization.*

typealias WooProductVariationListResponse = List<WooProductVariationResponse>


@Serializable
data class WooProductVariationResponse(
    val id: Int,
    val type: String,

    @SerialName("date_created")
    val dateCreated: String,

    @SerialName("date_created_gmt")
    val dateCreatedGmt: String,

    @SerialName("date_modified")
    val dateModified: String,

    @SerialName("date_modified_gmt")
    val dateModifiedGmt: String,

    val description: String,
    val permalink: String,
    val sku: String,

    @SerialName("global_unique_id")
    val globalUniqueID: String,

    val price: String,

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
    val onSale: Boolean,

    val status: String,
    val purchasable: Boolean,
    val virtual: Boolean,

    @SerialName("tax_status")
    val taxStatus: String,

    @SerialName("tax_class")
    val taxClass: String,

    @SerialName("manage_stock")
    val manageStock: Boolean,

    @SerialName("stock_quantity")
    val stockQuantity: Int? = null,

    @SerialName("stock_status")
    val stockStatus: String,

    val backorders: String,

    @SerialName("backorders_allowed")
    val backordersAllowed: Boolean,

    val backordered: Boolean,

    @SerialName("low_stock_amount")
    val lowStockAmount: Int? = null,

    val weight: String,
    val dimensions: Dimensions,

    @SerialName("shipping_class")
    val shippingClass: String,

    @SerialName("shipping_class_id")
    val shippingClassID: Long,

    val image: Image,

    val attributes: List<WooVariationAttribute>,

    @SerialName("menu_order")
    val menuOrder: Long,

    @SerialName("meta_data")
    val metaData: List<MetaDatum>,

    val name: String,

    @SerialName("parent_id")
    val parentID: Long,
)

@Serializable
data class WooVariationAttribute(
    val id: Int = 0,
    val name: String, // "Color"
    val slug: String? = null, // "pa_color"
    val option: String // "Red"
)


