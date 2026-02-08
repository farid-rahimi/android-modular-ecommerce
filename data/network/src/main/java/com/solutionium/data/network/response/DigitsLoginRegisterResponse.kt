package com.solutionium.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitsLoginRegisterResponse(

    @SerialName("success")
    val success: Boolean,

    @SerialName("data")
    val data: DigitsLoginRegisterData

)

@Serializable
data class DigitsLoginRegisterData(

    val message: String? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("access_token")
    val token: String? = null,

    @SerialName("token_type")
    val tokenType: String? = null,

    @SerialName("action")
    val action: String? = null

)
