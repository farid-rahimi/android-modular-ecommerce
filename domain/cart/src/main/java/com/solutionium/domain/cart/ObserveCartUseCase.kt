package com.solutionium.domain.cart

import com.solutionium.shared.data.model.CartItem
import kotlinx.coroutines.flow.Flow

interface ObserveCartUseCase {

    operator fun invoke(): Flow<List<CartItem>>

}