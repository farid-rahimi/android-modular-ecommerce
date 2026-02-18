package com.solutionium.domain.checkout.impl

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewOrderData
import com.solutionium.data.model.Order
import com.solutionium.data.model.Result
import com.solutionium.data.woo.checkout.CheckoutRepository
import com.solutionium.domain.checkout.CreateOrderUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateOrderUseCaseImpl(
    private val checkoutRepository: CheckoutRepository,
) : CreateOrderUseCase {
    override suspend fun invoke(orderData: NewOrderData): Flow<Result<Order, GeneralError>> = flow {
        emit(checkoutRepository.createOrder(orderData))
    }
}