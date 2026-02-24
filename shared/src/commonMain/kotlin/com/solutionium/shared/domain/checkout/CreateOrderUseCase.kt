package com.solutionium.shared.domain.checkout

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.NewOrderData
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface CreateOrderUseCase {

    suspend operator fun invoke(orderData: NewOrderData): Flow<Result<Order, GeneralError>>

}