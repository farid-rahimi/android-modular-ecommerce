package com.solutionium.shared.domain.products

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductVariation
import com.solutionium.shared.data.model.Result

interface GetProductVariationsUseCase {

    suspend operator fun invoke(productId: Int): Result<List<ProductVariation>, GeneralError>

}