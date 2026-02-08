package com.solutionium.domain.cart

import com.solutionium.data.model.CartItem
import kotlinx.coroutines.flow.Flow

interface ObserveCartUseCase {

    operator fun invoke(): Flow<List<CartItem>>

}