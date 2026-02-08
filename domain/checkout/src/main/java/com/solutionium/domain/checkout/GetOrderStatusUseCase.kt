package com.solutionium.domain.checkout

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Order
import com.solutionium.data.model.Result

interface GetOrderStatusUseCase {

    suspend operator fun invoke(orderId: Int): Result<Order, GeneralError>

}