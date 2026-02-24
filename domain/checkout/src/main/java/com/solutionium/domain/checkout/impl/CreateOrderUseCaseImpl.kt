package com.solutionium.domain.checkout.impl

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.NewOrderData
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.checkout.CheckoutRepository
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