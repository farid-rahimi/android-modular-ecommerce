package com.solutionium.domain.checkout.impl

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.ShippingMethod
import com.solutionium.data.woo.checkout.CheckoutRepository
import com.solutionium.domain.checkout.GetShippingMethodsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetShippingMethodsUseCaseImpl @Inject constructor(
    private val checkoutRepository: CheckoutRepository
) : GetShippingMethodsUseCase {
    override suspend fun invoke(): Flow<Result<List<ShippingMethod>, GeneralError>> = flow {
        emit(checkoutRepository.getShippingMethods())
    }
}