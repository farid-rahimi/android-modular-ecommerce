package com.solutionium.data.network.request

import com.solutionium.data.network.common.WooFeeLine
import com.solutionium.data.network.common.WooAddress
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest (


    @SerialName("created_via")
    val createdVia: String,

    val status: String? = null,

    val currency: String? = null,

    @SerialName("customer_id")
    val customerID: Long = 0,

    @SerialName("customer_note")
    val customerNote: String? = null,

    @SerialName("transaction_id")
    val transactionID: String? = null,

    @SerialName("payment_method")
    val paymentMethod: String,

    @SerialName("payment_method_title")
    val paymentMethodTitle: String,

    @SerialName("set_paid")
    val setPaid: Boolean? = null,

    @SerialName("meta_data")
    val metaData: List<OrderMetadata>? = null,

    val billing: WooAddress,
    val shipping: WooAddress,

    @SerialName("line_items")
    val lineItems: List<LineItemRequest>,

    @SerialName("shipping_lines")
    val shippingLines: List<ShippingLine>,

    @SerialName("fee_lines")
    val feeLines: List<WooFeeLine>? = null,

    @SerialName("coupon_lines")
    val couponLines: List<CouponLine>? = null
)



@Serializable
data class LineItemRequest (
    @SerialName("product_id")
    val productID: Int,

    val name: String? = null,

    @SerialName("subtotal")
    val subTotal: String? = null,
    val total: String? = null,

    val quantity: Int,

    @SerialName("variation_id")
    val variationID: Int? = null,

    @SerialName("meta_data")
    val metaData: List<OrderMetadata>? = null
)

@Serializable
data class ShippingLine (
    @SerialName("method_id")
    val methodID: String,

    @SerialName("method_title")
    val methodTitle: String,

    val total: String
)



@Serializable
data class CouponLine (
    val code: String
)

@Serializable
data class OrderMetadata(
    val key: String,
    val value: String
)