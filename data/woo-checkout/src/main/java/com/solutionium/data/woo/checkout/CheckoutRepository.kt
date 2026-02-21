package com.solutionium.data.woo.checkout

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.NewOrderData
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.PaymentGateway
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.ShippingMethod

interface CheckoutRepository {

    suspend fun getPaymentGateways(forcedEnabled: List<String> = emptyList()): Result<List<PaymentGateway>, GeneralError>

    suspend fun getShippingMethods(): Result<List<ShippingMethod>, GeneralError>

    suspend fun createOrder(
        orderData: NewOrderData
    ): Result<Order, GeneralError>



}