package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.shared.data.model.CartItem
import com.solutionium.domain.cart.AddToCartUseCase

internal class AddToCartUseCaseImpl(
    private val cartRepository: CartRepository
) : AddToCartUseCase {
    override suspend fun invoke(cartItem: CartItem) {
        cartRepository.addCartItem(cartItem)
    }
}