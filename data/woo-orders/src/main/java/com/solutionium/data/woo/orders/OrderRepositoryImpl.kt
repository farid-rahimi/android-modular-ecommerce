package com.solutionium.data.woo.orders

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.solutionium.shared.data.api.woo.WooOrderRemoteSource
import com.solutionium.shared.data.api.woo.WooProductsRemoteSource
import com.solutionium.data.local.TokenStore
import com.solutionium.shared.data.model.FilterKey
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.OrderFilterKey
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(
    private val orderRemoteSource: WooOrderRemoteSource,
    private val tokenStore: TokenStore,
) : OrderRepository {
    override fun getOrderListStream(queries: Map<String, String>): Flow<PagingData<Order>> =

        Pager(
            config = PagingConfig(
                pageSize = WooProductsRemoteSource.PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                OrdersPagingSource(
                    queries = queries.plus(OrderFilterKey.USER_ID.apiKey to (tokenStore.getUserId() ?: "-1")),
                    wooOrderRemoteSource = orderRemoteSource,
                )
            },
        ).flow

    override suspend fun getOrderById(orderId: Int): Result<Order, GeneralError> =
        orderRemoteSource.getOrderById(orderId)

    override suspend fun getLatestOrder(): Result<List<Order>, GeneralError> {
        return orderRemoteSource.getOrderList(
            1,
            mapOf(
                "per_page" to "1",
                FilterKey.ORDER_BY.apiKey to "date",
                FilterKey.ORDER.apiKey to "desc",
                OrderFilterKey.USER_ID.apiKey to (tokenStore.getUserId() ?: "-1")
            )
        )
    }

}