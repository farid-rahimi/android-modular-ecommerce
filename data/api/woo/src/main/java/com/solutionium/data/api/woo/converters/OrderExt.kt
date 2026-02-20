package com.solutionium.data.api.woo.converters

import com.solutionium.data.api.woo.BuildConfig.APP_VERSION_NAME
import com.solutionium.data.model.Address
import com.solutionium.data.model.CartItem
import com.solutionium.data.model.FeeLine
import com.solutionium.data.model.LineItem
import com.solutionium.data.model.Metadata
import com.solutionium.data.model.NewOrderData
import com.solutionium.data.model.Order

//import com.solutionium.data.network.BuildConfig.BASE_URL
//import com.solutionium.shared.BuildKonfig.BASE_URL
import com.solutionium.shared.data.network.common.WooAddress
import com.solutionium.shared.data.network.common.WooFeeLine
import com.solutionium.shared.data.network.request.CouponLine
import com.solutionium.shared.data.network.request.LineItemRequest
import com.solutionium.shared.data.network.request.OrderMetadata
import com.solutionium.shared.data.network.request.OrderRequest
import com.solutionium.shared.data.network.request.ShippingLine
import com.solutionium.shared.data.network.response.WooLineItem
import com.solutionium.shared.data.network.response.WooOrderResponse

fun NewOrderData.toRequestBody(): OrderRequest =
    OrderRequest(
        paymentMethod = paymentMethod,
        paymentMethodTitle = paymentMethodTitle,
        setPaid = setPaid,
        billing = billing.toRequestBody(),
        shipping = shipping.toRequestBody(),
        lineItems = cartItems.map { it.toRequestBody() },
        shippingLines = listOf(
            ShippingLine(
                methodID = shippingMethod.id.toString(),
                methodTitle = shippingMethod.title,
                total = shippingMethod.cost
            )
        ),
        feeLines = feeLines?.map { it.toWooFeeLine() },
        status = status,
        currency = currency,
        customerID = customerID,
        customerNote = customerNote,
        createdVia = "mobile_app_ver:$APP_VERSION_NAME",
        transactionID = null,
        couponLines = coupon?.map { CouponLine(it) },
        metaData = metaData.map { it.toRequestBody() },
    )

fun Address.toRequestBody(): WooAddress =
    WooAddress(
        firstName = firstName,
        lastName = lastName,
        company = company,
        address1 = address1,
        address2 = address2,
        city = city,
        state = state,
        postcode = postcode,
        country = country,
        email = email,
        phone = phone
    )

fun CartItem.toRequestBody(): LineItemRequest =
    LineItemRequest(
        productID = productId,
        quantity = quantity,
        variationID = if (isDecant) null else variationId,
        name = if (isDecant) name else null,
        subTotal = if (regularPrice != null) (regularPrice!! * quantity).toString() else (currentPrice * quantity).toString(),
        total = if (isDecant || appOffer > 0) (currentPrice * quantity).toString() else null,
        metaData = if (isDecant) listOf(
            OrderMetadata(
                key = "is_dec",
                value = "y"
            ),
            OrderMetadata(
                key = "d_vol",
                value = decVol ?: ""
            ),
        ) else null,
        //taxClass = "",
        //metaData = null
    )

fun Metadata.toRequestBody(): OrderMetadata =
    OrderMetadata(
        key = key,
        value = value
    )

fun WooOrderResponse.toModel(): Order = Order(
    id = id,
    total = total,
    status = status,
    dateCreated = dateCreated,
    datePaid = datePaid,
    dateCompleted = dateCompleted,
    paymentMethod = paymentMethod,
    paymentMethodTitle = paymentMethodTitle,
    orderKey = orderKey, //orderKey
    paymentUrl = "${"BASE_URL"}checkout/order-pay/${id}/?key=${orderKey}", //paymentUrl // TODO()
    lineItems = lineItems.map { it.toModel() }
)

fun WooLineItem.toModel() = LineItem (
    id = id,
    name = name,
    productId = productID,
    quantity = quantity,
    total = total,
    subTotal = subtotal,
    imageUrl = image?.src

)

fun FeeLine.toWooFeeLine() = WooFeeLine(
    name = name,
    total = total.toString(),
    metaData = metadata.map { it.toRequestBody() }
)
