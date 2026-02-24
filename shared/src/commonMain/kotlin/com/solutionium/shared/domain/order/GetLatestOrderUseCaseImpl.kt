package com.solutionium.shared.domain.order

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.orders.OrderRepository

class GetLatestOrderUseCaseImpl (
    private val orderRepository: OrderRepository
) : GetLatestOrderUseCase {
    override suspend fun invoke(): Result<List<Order>, GeneralError> =
        orderRepository.getLatestOrder()
}