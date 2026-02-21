package com.solutionium.domain.cart

import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.ProductDetail

interface AddToCartUseCase {

    suspend operator fun invoke(cartItem: CartItem)

}