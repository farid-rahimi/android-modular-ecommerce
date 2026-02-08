package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.data.model.CartItem
import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.toCartItem
import com.solutionium.domain.cart.AddToCartUseCase
import javax.inject.Inject

internal class AddToCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : AddToCartUseCase {
    override suspend fun invoke(cartItem: CartItem) {
        cartRepository.addCartItem(cartItem)
    }
}