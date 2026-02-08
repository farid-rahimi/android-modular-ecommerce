package com.solutionium.data.api.woo

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Order
import com.solutionium.data.model.Result

interface WooOrderRemoteSource {

    suspend fun getOrderList(
        page: Int,
        queries: Map<String, String>
    ): Result<List<Order>, GeneralError>


    suspend fun getOrderById(orderId: Int): Result<Order, GeneralError>

}