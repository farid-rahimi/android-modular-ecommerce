package com.solutionium.domain.cart

import com.solutionium.shared.data.model.CartItem
import kotlinx.coroutines.flow.Flow

interface GetCartItemByProductUseCase {
    suspend operator fun invoke(productId: Int, variationId: Int): Flow<CartItem?>
}