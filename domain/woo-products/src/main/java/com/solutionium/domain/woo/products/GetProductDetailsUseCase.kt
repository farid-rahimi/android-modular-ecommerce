package com.solutionium.domain.woo.products

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetProductDetailsUseCase {
    suspend operator fun invoke(productId: Int): Result<ProductDetail, GeneralError>
    suspend operator fun invoke(productSlug: String): Result<ProductDetail, GeneralError>
}