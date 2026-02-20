package com.solutionium.shared.data.network.response

import com.solutionium.shared.data.network.adapter.IntOrStringSerializer
import kotlinx.serialization.*

typealias ShippingMethodsResponse = List<ShippingMethodResponse>

@Serializable
data class ShippingMethodResponse (
    val id: Int? = null,

    @SerialName("instance_id")
    val instanceID: Int? = null,

    val title: String? = null,

    @Serializable(with = IntOrStringSerializer::class)
    val order: Int,
    val enabled: Boolean? = null,

    @SerialName("method_id")
    val methodID: String? = null,

    @SerialName("method_title")
    val methodTitle: String? = null,

    @SerialName("method_description")
    val methodDescription: String? = null,

    val settings: Map<String, ShippingMethodSetting>? = null,

    )


@Serializable
data class ShippingMethodSetting (
    val id: String? = null,
    val label: String? = null,
    val description: String? = null,
    val type: String? = null,
    val value: String? = null,
    val default: String? = null,
    val tip: String? = null,
    val placeholder: String? = null,
)
