package com.solutionium.shared.data.api.woo.impl

import com.solutionium.shared.data.api.woo.converters.toModel
import com.solutionium.shared.data.api.woo.converters.toRequestBody
import com.solutionium.shared.data.api.woo.converters.toShippingMethod
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.NewOrderData
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.PaymentGateway
import com.solutionium.shared.data.model.ShippingMethod
import com.solutionium.shared.data.api.woo.WooCheckoutRemoteSource
import com.solutionium.shared.data.api.woo.converters.toPaymentGateway
import com.solutionium.shared.data.api.woo.handleNetworkResponse
import com.solutionium.shared.data.network.clients.WooCheckoutOrderClient
import com.solutionium.shared.data.model.Result


internal class WooCheckoutRemoteSourceImpl(
    //private val wooCheckoutOrderService: WooCheckoutOrderService,
    private val checkoutOrderApi: WooCheckoutOrderClient
) : WooCheckoutRemoteSource {
    override suspend fun getPaymentGateways(forcedEnabled: List<String>): Result<List<PaymentGateway>, GeneralError> =
        handleNetworkResponse(
            networkCall = { checkoutOrderApi.getPaymentGateways() },
            mapper = { responseList ->
                responseList.filter { it.enabled == true || forcedEnabled.contains(it.id) }
                    .map { it.toPaymentGateway() }
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