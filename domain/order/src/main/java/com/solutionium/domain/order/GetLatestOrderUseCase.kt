package com.solutionium.domain.order

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Order
import com.solutionium.data.model.Result

interface GetLatestOrderUseCase {

    suspend operator fun invoke(): Result<List<Order>, GeneralError>

}