package com.solutionium.shared.domain.order

import androidx.paging.PagingData
import com.solutionium.shared.data.model.FilterCriterion
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.toQueryMap
import com.solutionium.shared.data.orders.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetOrderListPagingUseCaseImpl(
    private val orderRepository: OrderRepository
) : GetOrderListPagingUseCase {
    override fun invoke(filters: List<FilterCriterion>): Flow<PagingData<Order>> =
        orderRepository.getOrderListStream(filters.toQueryMap())
}