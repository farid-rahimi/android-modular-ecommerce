package com.solutionium.domain.checkout.impl

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Order
import com.solutionium.data.model.Result
import com.solutionium.data.woo.orders.OrderRepository
import com.solutionium.domain.checkout.GetOrderStatusUseCase

class GetOrderStatusUseCaseImpl(
    private val orderRepository: OrderRepository
) : GetOrderStatusUseCase {
    override suspend fun invoke(orderId: Int): Result<Order, GeneralError> {
        return orderRepository.getOrderById(orderId)
    }
}