package com.solutionium.shared.domain.products.impl

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductDetail
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.products.WooProductRepository
import com.solutionium.shared.domain.products.GetProductDetailsUseCase

internal class GetProductDetailsUseCaseImpl(
    private val wooProductRepository: WooProductRepository
) : GetProductDetailsUseCase {
    override suspend fun invoke(productId: Int): Result<ProductDetail, GeneralError> =
        wooProductRepository.getProductDetails(productId)

    override suspend fun invoke(productSlug: String): Result<ProductDetail, GeneralError> =
        wooProductRepository.getProductDetails(productSlug)
}