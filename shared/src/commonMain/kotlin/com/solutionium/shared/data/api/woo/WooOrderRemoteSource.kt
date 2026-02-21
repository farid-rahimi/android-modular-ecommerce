package com.solutionium.shared.data.api.woo

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Result


interface WooOrderRemoteSource {

    suspend fun getOrderList(
        page: Int,
        queries: Map<String, String>
    ): Result<List<Order>, GeneralError>


    suspend fun getOrderById(orderId: Int): Result<Order, GeneralError>

}