package com.solutionium.data.network.response


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class WooWalletConfigResponse (
    val success: Boolean,
    val cashback: Cashback,
    val settings: Settings
)

@Serializable
data class Cashback (
    val enabled: Boolean,
    val statuses: List<String>,
    val rule: String,
    val type: String,
    val amount: Long,

    @SerialName("min_cart_amount")
    val minCartAmount: Long,

    @SerialName("max_cashback_amount")
    val maxCashbackAmount: Long,

    @SerialName("allow_min_cashback")
    val allowMinCashback: Boolean
)

@Serializable
data class Settings (
    @SerialName("enable_wallet_topup")
    val enableWalletTopup: Boolean,

    @SerialName("product_title")
    val productTitle: String,

    @SerialName("product_image")
    val productImage: String,

    @SerialName("min_topup_amount")
    val minTopupAmount: Long,

    @SerialName("max_topup_amount")
    val maxTopupAmount: Long,

    @SerialName("allowed_payment_gateways")
    val allowedPaymentGateways: List<String>,

    @SerialName("enable_gateway_charge")
    val enableGatewayCharge: Boolean,

    @SerialName("gateway_charge_type")
    val gatewayChargeType: String,

    @SerialName("enable_partial_payment")
    val enablePartialPayment: Boolean,

    @SerialName("auto_deduct_partial_payment")
    val autoDeductPartialPayment: Boolean,

    @SerialName("enable_wallet_transfer")
    val enableWalletTransfer: Boolean,

    @SerialName("min_transfer_amount")
    val minTransferAmount: Long,

    @SerialName("transfer_charge_type")
    val transferChargeType: String,

    @SerialName("transfer_charge_amount")
    val transferChargeAmount: Long
)