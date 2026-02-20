package com.solutionium.shared.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


typealias WooBrandListResponse = List<WooBrandResponse>

@Serializable
data class WooBrandResponse (
    val id: Int,
    val name: String,
    val slug: String,
    val parent: Int,
    val description: String,
    val display: String,
    val image: Image? = null,

    @SerialName("menu_order")
    val menuOrder: Int? = null,

    val count: Int? = null
)

