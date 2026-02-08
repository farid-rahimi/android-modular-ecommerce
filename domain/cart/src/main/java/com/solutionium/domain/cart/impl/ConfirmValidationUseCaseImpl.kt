package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.domain.cart.ConfirmValidationUseCase
import javax.inject.Inject

internal class ConfirmValidationUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
): ConfirmValidationUseCase {
    override suspend fun invoke() {
        cartRepository.confirmValidation()
    }
}