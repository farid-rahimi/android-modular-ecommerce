package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.domain.cart.ClearCartUseCase

class ClearCartUseCaseImpl(
    private val cartRepository: CartRepository
) : ClearCartUseCase {

    override suspend fun invoke() = cartRepository.clearCart()

}