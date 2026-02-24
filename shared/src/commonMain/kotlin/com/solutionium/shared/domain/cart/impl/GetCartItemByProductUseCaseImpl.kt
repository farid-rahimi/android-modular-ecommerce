package com.solutionium.shared.domain.cart.impl

import com.solutionium.shared.data.cart.CartRepository
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.domain.cart.GetCartItemByProductUseCase
import kotlinx.coroutines.flow.Flow

class GetCartItemByProductUseCaseImpl(
    private val cartRepository: CartRepository
) : GetCartItemByProductUseCase {
    override suspend fun invoke(productId: Int, variationId: Int): Flow<CartItem?> =
        cartRepository.getCartItemByProduct(productId, variationId)

}