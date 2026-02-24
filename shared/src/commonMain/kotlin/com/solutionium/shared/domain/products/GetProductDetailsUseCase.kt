package com.solutionium.shared.domain.products

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductDetail
import com.solutionium.shared.data.model.Result

interface GetProductDetailsUseCase {
    suspend operator fun invoke(productId: Int): Result<ProductDetail, GeneralError>
    suspend operator fun invoke(productSlug: String): Result<ProductDetail, GeneralError>
}