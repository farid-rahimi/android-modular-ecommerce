package com.solutionium.domain.cart

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface ValidateCartUseCase {
    suspend operator fun invoke(): Flow<Result<List<CartItemValidationResult>, GeneralError>>
}