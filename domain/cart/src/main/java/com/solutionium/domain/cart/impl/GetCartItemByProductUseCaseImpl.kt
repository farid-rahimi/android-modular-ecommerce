package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.data.model.CartItem
import com.solutionium.domain.cart.GetCartItemByProductUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemByProductUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : GetCartItemByProductUseCase {
    override suspend fun invoke(productId: Int, variationId: Int): Flow<CartItem?> =
        cartRepository.getCartItemByProduct(productId, variationId)

}