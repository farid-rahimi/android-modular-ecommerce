package com.solutionium.shared.data.network.response

import kotlinx.serialization.*

typealias PaymentGatewayListResponse = List<PaymentGatewayResponse>

@Serializable
data class PaymentGatewayResponse (
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,

    //@Serializable(with = IntOrStringSerializer::class)
    //var order: Int?,
    val enabled: Boolean? = null,

    @SerialName("method_title")
    val methodTitle: String? = null,

    @SerialName("method_description")
    val methodDescription: String? = null,

    @SerialName("method_supports")
    val methodSupports: List<String>? = null,

    val settings: Map<String, PaymentGatewaySetting>? = null,

    )




@Serializable
data class PaymentGatewaySetting (
    val id: String? = null,
    val label: String? = null,
    val description: String? = null,
    val type: String? = null,
    val value: String? = null,
    val default: String? = null,
    val tip: String? = null,
    val placeholder: String? = null,
)

