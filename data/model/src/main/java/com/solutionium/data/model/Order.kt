package com.solutionium.data.model

data class Order(
    val id: Int,
    val total: String,
    val status: String,
    val dateCreated: String,
    val datePaid: String? = null,
    val dateCompleted: String? = null,
    val paymentMethod: String,
    val paymentMethodTitle: String,
    val orderKey: String? = null,
    val paymentUrl: String? = null,
    val lineItems: List<LineItem>
)

// Represents the full details of a single order
data class OrderDetails(
    val id: Int,
    val orderNumber: String,
    val status: String,
    val dateCreated: String,
    val total: String,
    val lineItems: List<LineItem>,
    val billingAddress: Address,
    val shippingAddress: Address,
    val paymentMethod: String
)

data class NewOrderData(

    val status: String? = null,

    val currency: String? = null,

    val customerID: Long = 0,

    val customerNote: String? = null,

    val paymentMethod: String,

    val paymentMethodTitle: String,

    val setPaid: Boolean? = null,

    val billing: Address,
    val shipping: Address,

    val cartItems: List<CartItem>,

    val shippingMethod: ShippingMethod,

    val feeLines: List<FeeLine>? = null,


    val coupon: List<String>? = null,

    val metaData: List<Metadata> = emptyList(),


    //val orderKey: String,
    //val paymentUrl: String

) {
    constructor(
        paymentMethod: String,
        paymentMethodTitle: String,
        billing: Address,
        shipping: Address,
        cartItems: List<CartItem>,
        shippingMethod: ShippingMethod,
        metaData: List<Metadata> = emptyList(),
        setPaid: Boolean? = null,
        status: String? = null,
    ) : this(
        coupon = null,
        paymentMethod = paymentMethod,
        paymentMethodTitle = paymentMethodTitle,
        billing = billing,
        shipping = shipping,
        cartItems = cartItems,
        shippingMethod = shippingMethod,
        metaData = metaData,
        setPaid = setPaid,
        status = status
    )


}

fun getPaymentRedirectUrl() = Metadata(
    key = "app_payment_redirect_url",
    value = "solutionium://payment-return"
)

fun getPartialPaymentAmount(value: String) = Metadata(
    key = "_partial_pay_through_wallet_amount",
    value = value
)

data class Metadata(
    val key: String,
    val value: String
)

data class FeeLine(
    val name: String,
    val total: Double,
    val metadata: List<Metadata> = emptyList()
)

fun getWalletPartialPaymentMeta() = Metadata(key = "_legacy_fee_key", value = "_via_wallet_partial_payment")

// Represents a single item within an order
data class LineItem(
    val id: Int,
    val name: String,
    val productId: Int,
    val quantity: Int,
    val total: String,
    val subTotal: String?,
    val imageUrl: String?
)

