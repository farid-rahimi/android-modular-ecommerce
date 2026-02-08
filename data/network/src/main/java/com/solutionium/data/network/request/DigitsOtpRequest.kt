package com.solutionium.data.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DigitsOtpRequest(

    @SerialName("mobileNo")
    val mobileNo: String,

    @SerialName("countrycode") // User’s Phone number country code (with + symbol)
    val countryCode: String = "+98",

    @SerialName("type") // login, register, resetpass, update
    val type: String? = null,

    @SerialName("whatsapp") // 1 (only if whatsapp is being used)
    val whatsapp: Int? = null,

    @SerialName("username") //(for send_otp and type = register) if you want to validate and check if user exists with username before sending OTP
    val username: String? = null,

    @SerialName("email") //(for send_otp and type = register) if you want to validate and check if user exists with email before sending OTP
    val email: String? = null,

)
