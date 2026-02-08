package com.solutionium.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias WooAttributeListResponse = List<WooAttributeResponse>

@Serializable
data class WooAttributeResponse(
    val id: Int,
    val name: String,
    val slug: String,
    val description: String,
    @SerialName("menu_order")
    val menuOrder: Int,
    val count: Int,
)
