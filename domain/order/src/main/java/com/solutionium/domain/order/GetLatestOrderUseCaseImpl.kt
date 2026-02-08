package com.solutionium.domain.order

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Order
import com.solutionium.data.model.Result
import com.solutionium.data.woo.orders.OrderRepository
import javax.inject.Inject

class GetLatestOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetLatestOrderUseCase {
    override suspend fun invoke(): Result<List<Order>, GeneralError> =
        orderRepository.getLatestOrder()
}