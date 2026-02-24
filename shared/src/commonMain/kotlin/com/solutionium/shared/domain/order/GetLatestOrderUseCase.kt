package com.solutionium.shared.domain.order

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Result

interface GetLatestOrderUseCase {

    suspend operator fun invoke(): Result<List<Order>, GeneralError>

}