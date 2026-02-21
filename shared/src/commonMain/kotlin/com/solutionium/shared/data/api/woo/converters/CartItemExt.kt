package com.solutionium.shared.data.api.woo.converters

import com.solutionium.shared.data.model.CartItemServer
import com.solutionium.shared.data.network.response.CartCheckResponse

fun CartCheckResponse.toModel() = CartItemServer(
    id = id,
    price = price?.toDouble() ?: 0.0,
    regularPrice = regularPrice?.toDouble() ?: 0.0,
    manageStock = manageStock ?: false,
    stockQuantity = stockQuantity ?: 0,
    stockStatus = stockStatus ?: "outofstock",
    backOrder = backOrder
)