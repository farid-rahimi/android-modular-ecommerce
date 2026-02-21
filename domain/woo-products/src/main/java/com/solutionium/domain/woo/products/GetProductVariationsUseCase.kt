package com.solutionium.domain.woo.products

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductVariation
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetProductVariationsUseCase {

    suspend operator fun invoke(productId: Int): Result<List<ProductVariation>, GeneralError>

}