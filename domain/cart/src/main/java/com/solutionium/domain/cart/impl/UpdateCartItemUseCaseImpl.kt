package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.data.model.CartItem
import com.solutionium.domain.cart.UpdateCartItemUseCase

class UpdateCartItemUseCaseImpl(
    private val cartRepository: CartRepository
) : UpdateCartItemUseCase {

    override suspend fun increaseCartItemQuantity(cartItemId: Int, variationId: Int) =
        cartRepository.increaseCartItemQuantity(cartItemId, variationId)

    override suspend fun decreaseCartItemQuantity(cartItemId: Int, variationId: Int) =
        cartRepository.decreaseCartItemQuantity(cartItemId, variationId)

    override suspend fun removeCartItem(cartItem: CartItem) =
        cartRepository.removeCartItem(cartItem)
}