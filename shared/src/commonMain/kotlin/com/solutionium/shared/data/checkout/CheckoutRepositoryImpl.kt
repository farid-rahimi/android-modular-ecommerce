package com.solutionium.shared.data.checkout

import com.solutionium.shared.data.api.woo.WooCheckoutRemoteSource
import com.solutionium.shared.data.local.TokenStore
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.NewOrderData
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.PaymentGateway
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.ShippingMethod

class CheckoutRepositoryImpl(
    private val checkoutRemoteSource: WooCheckoutRemoteSource,
    private val tokenStore: TokenStore,
) : CheckoutRepository {
    override suspend fun getPaymentGateways(forcedEnabled: List<String>): Result<List<PaymentGateway>, GeneralError> =
        checkoutRemoteSource.getPaymentGateways(forcedEnabled)

    override suspend fun getShippingMethods(): Result<List<ShippingMethod>, GeneralError> =
        checkoutRemoteSource.getShippingMethods()

    override suspend fun createOrder(orderData: NewOrderData): Result<Order, GeneralError> =
        checkoutRemoteSource.createOrder(orderData.copy(customerID = tokenStore.getUserId()?.toLong() ?: 0L))



}