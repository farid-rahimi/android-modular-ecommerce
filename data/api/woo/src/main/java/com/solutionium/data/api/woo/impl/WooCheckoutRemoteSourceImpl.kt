package com.solutionium.data.api.woo.impl

import com.solutionium.data.api.woo.WooCheckoutRemoteSource
import com.solutionium.data.api.woo.converters.toModel
import com.solutionium.data.api.woo.converters.toPaymentGateway
import com.solutionium.data.api.woo.converters.toRequestBody
import com.solutionium.data.api.woo.converters.toShippingMethod
import com.solutionium.data.api.woo.handleNetworkResponse
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewOrderData
import com.solutionium.data.model.Order
import com.solutionium.data.model.PaymentGateway
import com.solutionium.data.model.Result
import com.solutionium.data.model.ShippingMethod
import com.solutionium.data.network.clients.WooCheckoutOrderClient

internal class WooCheckoutRemoteSourceImpl(
    //private val wooCheckoutOrderService: WooCheckoutOrderService,
    private val checkoutOrderApi: WooCheckoutOrderClient
) : WooCheckoutRemoteSource {
    override suspend fun getPaymentGateways(forcedEnabled: List<String>): Result<List<PaymentGateway>, GeneralError> =
        handleNetworkResponse(
            networkCall = { checkoutOrderApi.getPaymentGateways() },
            mapper = { responseList ->
                responseList.filter { it.enabled == true || forcedEnabled.contains(it.id) }.map { it.toPaymentGateway() }
            }
        )


    override suspend fun getShippingMethods(): Result<List<ShippingMethod>, GeneralError> =
        handleNetworkResponse(
            networkCall = { checkoutOrderApi.getShippingMethods() },
            mapper = { responseList ->
                responseList.filter { it.enabled == true }.map { it.toShippingMethod() }
            }
        )


    override suspend fun createOrder(orderData: NewOrderData): Result<Order, GeneralError> =
        handleNetworkResponse(
            networkCall = { checkoutOrderApi.createOrder(orderData.toRequestBody()) },
            mapper = { response -> response.toModel() }
        )




}