package com.solutionium.domain.cart

import com.solutionium.data.model.CartItem

interface UpdateCartItemUseCase {

    suspend fun increaseCartItemQuantity(cartItemId: Int, variationId: Int = 0)

    suspend fun decreaseCartItemQuantity(cartItemId: Int, variationId: Int = 0)

    suspend fun removeCartItem(cartItem: CartItem)

}