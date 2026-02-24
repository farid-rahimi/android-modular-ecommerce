package com.solutionium.shared.data.cart

import com.solutionium.shared.data.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun addCartItem(cartItem: CartItem)

    suspend fun removeCartItem(cartItem: CartItem)

    suspend fun updateCartItem(cartItem: CartItem)

    suspend fun increaseCartItemQuantity(cartItemId: Int, variationId: Int)

    suspend fun decreaseCartItemQuantity(cartItemId: Int, variationId: Int)

    suspend fun clearCart()
    fun getCartItemByProduct(productId: Int, variationId: Int ): Flow<CartItem?>

    fun getCartItems(): Flow<List<CartItem>>

    suspend fun confirmValidation()

    //fun getItemQuantityByProduct(productId: Int): Flow<CartItem>

}