package com.solutionium.domain.cart.impl

import com.solutionium.shared.data.cart.CartRepository
import com.solutionium.domain.cart.ConfirmValidationUseCase

internal class ConfirmValidationUseCaseImpl(
    private val cartRepository: CartRepository
): ConfirmValidationUseCase {
    override suspend fun invoke() {
        cartRepository.confirmValidation()
    }
}