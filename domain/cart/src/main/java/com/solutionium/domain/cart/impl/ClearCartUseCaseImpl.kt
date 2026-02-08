package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.domain.cart.ClearCartUseCase
import javax.inject.Inject

class ClearCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : ClearCartUseCase {

    override suspend fun invoke() = cartRepository.clearCart()

}