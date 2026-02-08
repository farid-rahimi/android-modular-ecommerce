package com.solutionium.domain.woo.products.impl

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.Result
import com.solutionium.data.woo.products.WooProductRepository
import com.solutionium.domain.woo.products.GetProductDetailsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetProductDetailsUseCaseImpl @Inject constructor(
    private val wooProductRepository: WooProductRepository
) : GetProductDetailsUseCase {
    override suspend fun invoke(productId: Int): Result<ProductDetail, GeneralError> =
        wooProductRepository.getProductDetails(productId)

    override suspend fun invoke(productSlug: String): Result<ProductDetail, GeneralError> =
        wooProductRepository.getProductDetails(productSlug)
}