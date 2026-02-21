package com.solutionium.domain.checkout.impl

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.ShippingMethod
import com.solutionium.data.woo.checkout.CheckoutRepository
import com.solutionium.domain.checkout.GetShippingMethodsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetShippingMethodsUseCaseImpl(
    private val checkoutRepository: CheckoutRepository
) : GetShippingMethodsUseCase {
    override suspend fun invoke(): Flow<Result<List<ShippingMethod>, GeneralError>> = flow {
        emit(checkoutRepository.getShippingMethods())
    }
}