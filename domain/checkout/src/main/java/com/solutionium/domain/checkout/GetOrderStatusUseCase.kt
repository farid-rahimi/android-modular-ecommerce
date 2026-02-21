package com.solutionium.domain.checkout

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Result

interface GetOrderStatusUseCase {

    suspend operator fun invoke(orderId: Int): Result<Order, GeneralError>

}