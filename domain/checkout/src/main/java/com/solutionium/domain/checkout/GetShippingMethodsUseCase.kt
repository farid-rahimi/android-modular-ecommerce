package com.solutionium.domain.checkout

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.ShippingMethod
import kotlinx.coroutines.flow.Flow

interface GetShippingMethodsUseCase {

    suspend operator fun invoke() : Flow<Result<List<ShippingMethod>, GeneralError>>

}