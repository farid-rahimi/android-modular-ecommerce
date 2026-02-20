package com.solutionium.shared.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CartCheckListResponse = List<CartCheckResponse>

@Serializable
data class CartCheckResponse (

    @SerialName("i")
    val id: Int,

    @SerialName("p")
    val price: String?,

    @SerialName("r")
    val regularPrice: String?,

    @SerialName("m")
    val manageStock: Boolean?,

    @SerialName("sq")
    val stockQuantity: Int?,

    @SerialName("ss")
    val stockStatus: String?,

    @SerialName("bo")
    val backOrder: String

)

@Serializable
data class CartCheckError (

    @SerialName("error")
    val error: String?,

)
