package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.data.model.CartItem
import com.solutionium.domain.cart.ObserveCartUseCase
import kotlinx.coroutines.flow.Flow

class ObserveCartUseCaseImpl(
    private val cartRepository: CartRepository
) : ObserveCartUseCase {
    override fun invoke(): Flow<List<CartItem>> = cartRepository.getCartItems()

}