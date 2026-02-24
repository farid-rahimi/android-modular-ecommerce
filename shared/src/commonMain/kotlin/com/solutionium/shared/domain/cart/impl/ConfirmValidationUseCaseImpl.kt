package com.solutionium.shared.domain.cart.impl

import com.solutionium.shared.data.cart.CartRepository
import com.solutionium.shared.domain.cart.ConfirmValidationUseCase

internal class ConfirmValidationUseCaseImpl(
    private val cartRepository: CartRepository
): ConfirmValidationUseCase {
    override suspend fun invoke() {
        cartRepository.confirmValidation()
    }
}