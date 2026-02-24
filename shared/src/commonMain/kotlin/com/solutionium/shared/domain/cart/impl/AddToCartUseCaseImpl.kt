package com.solutionium.shared.domain.cart.impl

import com.solutionium.shared.data.cart.CartRepository
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.domain.cart.AddToCartUseCase

internal class AddToCartUseCaseImpl(
    private val cartRepository: CartRepository
) : AddToCartUseCase {
    override suspend fun invoke(cartItem: CartItem) {
        cartRepository.addCartItem(cartItem)
    }
}