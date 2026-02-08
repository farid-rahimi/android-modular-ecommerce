package com.solutionium.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WpUserResponse (
    val id: Int? = null,
    val name: String? = null,

    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    val nickname: String? = null,

    val url: String? = null,

    @SerialName("email")
    val email: String? = null,

    @SerialName("phone_number")
    val phone: String? = null,
    val description: String? = null,
    val link: String? = null,
    val slug: String? = null,

    @SerialName("is_super_admin")
    val isSuperAdmin: Boolean? = null,

    @SerialName("wallet_balance")
    val walletBalance: String? = null,
)
