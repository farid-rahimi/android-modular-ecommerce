package com.solutionium.shared.domain.checkout.impl

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.orders.OrderRepository
import com.solutionium.shared.domain.checkout.GetOrderStatusUseCase

class GetOrderStatusUseCaseImpl(
    private val orderRepository: OrderRepository
) : GetOrderStatusUseCase {
    override suspend fun invoke(orderId: Int): Result<Order, GeneralError> {
        return orderRepository.getOrderById(orderId)
    }
}