package com.solutionium.data.api.woo.impl

import com.solutionium.data.api.woo.WooOrderRemoteSource
import com.solutionium.data.api.woo.converters.toModel
import com.solutionium.data.api.woo.converters.toProductThumbnail
import com.solutionium.data.api.woo.handleNetworkResponse
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewOrderData
import com.solutionium.data.model.Order
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result
import com.solutionium.data.network.services.WooOrderService
import javax.inject.Inject

class WooOrderRemoteSourceImpl @Inject constructor(
    private val wooOrderService: WooOrderService
): WooOrderRemoteSource {

    override suspend fun getOrderList(
        page: Int,
        queries: Map<String, String>
    ): Result<List<Order>, GeneralError> =
        handleNetworkResponse(
            networkCall = { wooOrderService.getOrders(page, queries) },
            mapper = { responseList ->
                responseList.map { it.toModel() }
            }
        )

    override suspend fun getOrderById(orderId: Int): Result<Order, GeneralError> =
        handleNetworkResponse(
            networkCall = { wooOrderService.getOrderById(orderId) },
            mapper = { response -> response.toModel() }
        )

}