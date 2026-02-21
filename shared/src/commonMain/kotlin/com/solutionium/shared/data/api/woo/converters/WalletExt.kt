package com.solutionium.shared.data.api.woo.converters

import com.solutionium.shared.data.model.Cashback
import com.solutionium.shared.data.model.Settings
import com.solutionium.shared.data.model.Transaction
import com.solutionium.shared.data.model.Type
import com.solutionium.shared.data.model.UserWallet
import com.solutionium.shared.data.model.WalletConfig
import com.solutionium.shared.data.network.response.WooUserWalletResponse
import com.solutionium.shared.data.network.response.WooWalletConfigResponse

fun WooUserWalletResponse.toUserWallet(): UserWallet =
    UserWallet(
        balance = this.balance,
        transactions = this.transactions.map {
            Transaction(
                id = it.id,
                type = it.type.toTransactionType(),
                amount = it.amount,
                balance = it.balance,
                currency = it.currency,
                details = it.details,
                date = it.date
            )
        }
    )

fun String.toTransactionType(): Type = when(this.lowercase()) {
    "credit" -> Type.Credit
    "debit" -> Type.Debit
    else -> Type.Unknown
}

fun WooWalletConfigResponse.toWalletConfig(): WalletConfig = WalletConfig(
    cashback = Cashback(
        enabled = cashback.enabled,
        statuses = cashback.statuses,
        rule = cashback.rule,
        type = cashback.type,
        amount = cashback.amount,
        minCartAmount = cashback.minCartAmount,
        maxCashbackAmount = cashback.maxCashbackAmount,
        allowMinCashback = cashback.allowMinCashback
    ),
    settings = Settings(
        enableWalletTopup = settings.enableWalletTopup,
        productTitle = settings.productTitle,
        productImage = settings.productImage,
        minTopupAmount = settings.minTopupAmount,
        maxTopupAmount = settings.maxTopupAmount,
        allowedPaymentGateways = settings.allowedPaymentGateways,
        enableGatewayCharge = settings.enableGatewayCharge,
        gatewayChargeType = settings.gatewayChargeType,
        enablePartialPayment = settings.enablePartialPayment
    )
)