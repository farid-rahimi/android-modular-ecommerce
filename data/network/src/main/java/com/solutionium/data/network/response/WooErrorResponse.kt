package com.solutionium.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class WooErrorResponse(

    @SerialName("code")
    val code: String,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: WooErrorData?
)

@Serializable
class WooErrorData (

    @SerialName("status")
    val status: Int = 0

)
