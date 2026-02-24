package com.solutionium.shared.domain.cart

import com.solutionium.shared.data.model.CartItem

interface AddToCartUseCase {

    suspend operator fun invoke(cartItem: CartItem)

}