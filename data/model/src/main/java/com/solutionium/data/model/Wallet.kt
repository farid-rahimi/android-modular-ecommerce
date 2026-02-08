package com.solutionium.data.model


data class UserWallet(
    val balance: Double,
    val transactions: List<Transaction>
)

data class Transaction (
    val id: Long,
    val type: Type,
    val amount: Double,
    val balance: Double,
    val currency: String,
    val details: String,
    val date: String
)

enum class Type {
    Credit,
    Debit,
    Unknown;
}



data class WalletConfig(
    val cashback: Cashback,
    val settings: Settings
)
data class Cashback(
    val enabled: Boolean,
    val statuses: List<String>,
    val rule: String,
    val type: String,
    val amount: Long,
    val minCartAmount: Long,
    val maxCashbackAmount: Long,
    val allowMinCashback: Boolean
)

data class Settings(
    val enableWalletTopup: Boolean,
    val productTitle: String,
    val productImage: String,
    val minTopupAmount: Long,
    val maxTopupAmount: Long,
    val allowedPaymentGateways: List<String>,
    val enableGatewayCharge: Boolean,
    val gatewayChargeType: String,
    val enablePartialPayment: Boolean
)