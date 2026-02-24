package com.solutionium.shared.domain.cart.impl

import com.solutionium.shared.data.cart.CartRepository
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.domain.cart.UpdateCartItemUseCase

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