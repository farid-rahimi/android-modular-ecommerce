package com.solutionium.shared.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitsErrorResponse (

    @SerialName("success")
    val success: Boolean? = null,

    @SerialName("data")
    val data: DigitsErrorData?
)

@Serializable
data class DigitsErrorData (

    @SerialName("code")
    val code: String? = null,

    @SerialName("msg")
    val msg: String? = null,

)
