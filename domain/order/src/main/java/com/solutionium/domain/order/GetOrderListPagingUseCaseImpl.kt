package com.solutionium.domain.order

import androidx.paging.PagingData
import com.solutionium.data.model.FilterCriterion
import com.solutionium.data.model.Order
import com.solutionium.data.model.toQueryMap
import com.solutionium.data.woo.orders.OrderRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetOrderListPagingUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetOrderListPagingUseCase {
    override fun invoke(filters: List<FilterCriterion>): Flow<PagingData<Order>> =
        orderRepository.getOrderListStream(filters.toQueryMap())
}