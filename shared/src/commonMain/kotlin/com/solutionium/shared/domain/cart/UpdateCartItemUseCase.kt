package com.solutionium.shared.domain.cart

import com.solutionium.shared.data.model.CartItem

interface UpdateCartItemUseCase {

    suspend fun increaseCartItemQuantity(cartItemId: Int, variationId: Int = 0)

    suspend fun decreaseCartItemQuantity(cartItemId: Int, variationId: Int = 0)

    suspend fun removeCartItem(cartItem: CartItem)

}