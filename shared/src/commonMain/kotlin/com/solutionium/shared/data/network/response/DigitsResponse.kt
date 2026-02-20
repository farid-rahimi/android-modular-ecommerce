package com.solutionium.shared.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Both of them can be used for this endpoints: send_otp, verify_otp, resend_otp


@Serializable
data class DigitsSimpleResponse (

    @SerialName("success")
    val success: Boolean,

)

@Serializable
data class DigitsOtpResponse(

    @SerialName("accountkit") //If accountkit is 1, then you need to use account kit for verification
    val accountKit: Int,

    @SerialName("firebase") //If firebase is 1, then you need to use firebase for verification
    val firebase: Int,

    @SerialName("code") //If code is 1, then it’s a success
    val code: Int,

)

@Serializable
data class DigitsOtpErrorResponse(

    @SerialName("code")
    val code: Int,

    @SerialName("message")
    val message: String

)

