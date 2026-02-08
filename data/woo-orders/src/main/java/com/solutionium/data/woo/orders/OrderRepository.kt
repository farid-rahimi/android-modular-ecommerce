package com.solutionium.data.woo.orders

import androidx.paging.PagingData
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Order
import com.solutionium.data.model.Result
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getOrderListStream(
        queries: Map<String, String>
    ): Flow<PagingData<Order>>

    suspend fun getOrderById(orderId: Int): Result<Order, GeneralError>

    suspend fun getLatestOrder(): Result<List<Order>, GeneralError>

}