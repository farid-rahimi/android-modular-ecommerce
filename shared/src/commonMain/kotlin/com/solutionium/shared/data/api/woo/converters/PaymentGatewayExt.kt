package com.solutionium.shared.data.api.woo.converters

import com.solutionium.shared.data.model.PaymentGateway
import com.solutionium.shared.data.network.response.PaymentGatewayResponse

fun PaymentGatewayResponse.toPaymentGateway(): PaymentGateway =
    PaymentGateway(
        id = id ?: "",
        title = title ?: "",
        description = description ?: "",
    )