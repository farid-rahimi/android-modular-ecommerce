package com.solutionium.domain.checkout

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.NewOrderData
import com.solutionium.data.model.Order
import com.solutionium.data.model.Result
import kotlinx.coroutines.flow.Flow

interface CreateOrderUseCase {

    suspend operator fun invoke(orderData: NewOrderData): Flow<Result<Order, GeneralError>>

}