package com.solutionium.domain.cart

import com.solutionium.data.model.CartItem
import com.solutionium.data.model.ProductDetail

interface AddToCartUseCase {

    suspend operator fun invoke(cartItem: CartItem)

}