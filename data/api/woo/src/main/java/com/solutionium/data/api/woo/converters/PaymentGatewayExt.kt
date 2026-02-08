package com.solutionium.data.api.woo.converters

import com.solutionium.data.model.PaymentGateway
import com.solutionium.data.network.response.PaymentGatewayResponse

fun PaymentGatewayResponse.toPaymentGateway(): PaymentGateway =
    PaymentGateway(
        id = id ?: "",
        title = title ?: "",
        description = description ?: "",
    )