package com.solutionium.shared.domain.checkout

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.ShippingMethod
import kotlinx.coroutines.flow.Flow

interface GetShippingMethodsUseCase {

    suspend operator fun invoke() : Flow<Result<List<ShippingMethod>, GeneralError>>

}