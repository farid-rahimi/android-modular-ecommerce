package com.solutionium.domain.woo.products

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductDetail
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetProductDetailsUseCase {
    suspend operator fun invoke(productId: Int): Result<ProductDetail, GeneralError>
    suspend operator fun invoke(productSlug: String): Result<ProductDetail, GeneralError>
}