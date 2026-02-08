package com.solutionium.domain.cart

import com.solutionium.data.model.CartItem
import kotlinx.coroutines.flow.Flow

interface GetCartItemByProductUseCase {
    suspend operator fun invoke(productId: Int, variationId: Int): Flow<CartItem?>
}