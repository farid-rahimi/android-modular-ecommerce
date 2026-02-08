package com.solutionium.data.network.response


import kotlinx.serialization.*

@Serializable
data class WooUserWalletResponse (
    val success: Boolean,

    @SerialName("user_id")
    val userID: Long,

    val balance: Double,
    val transactions: List<Transaction>
)

@Serializable
data class Transaction (
    val id: Long,
    val type: String,
    val amount: Double,
    val balance: Double,
    val currency: String,
    val details: String,
    val date: String
)


