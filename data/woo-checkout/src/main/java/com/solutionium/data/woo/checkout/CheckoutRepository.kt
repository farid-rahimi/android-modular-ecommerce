package com.solutionium.data.woo.checkout

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewOrderData
import com.solutionium.data.model.Order
import com.solutionium.data.model.PaymentGateway
import com.solutionium.data.model.Result
import com.solutionium.data.model.ShippingMethod

interface CheckoutRepository {

    suspend fun getPaymentGateways(forcedEnabled: List<String> = emptyList()): Result<List<PaymentGateway>, GeneralError>

    suspend fun getShippingMethods(): Result<List<ShippingMethod>, GeneralError>

    suspend fun createOrder(
        orderData: NewOrderData
    ): Result<Order, GeneralError>



}