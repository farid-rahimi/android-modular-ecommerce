package com.solutionium.data.woo.checkout

import com.solutionium.data.api.woo.WooCheckoutRemoteSource
import com.solutionium.data.local.TokenStore
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewOrderData
import com.solutionium.data.model.Order
import com.solutionium.data.model.PaymentGateway
import com.solutionium.data.model.Result
import com.solutionium.data.model.ShippingMethod
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(
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