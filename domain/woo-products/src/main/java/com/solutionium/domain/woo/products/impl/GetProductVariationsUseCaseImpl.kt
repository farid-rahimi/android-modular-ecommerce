package com.solutionium.domain.woo.products.impl

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductVariation
import com.solutionium.data.model.Result
import com.solutionium.data.woo.products.WooProductRepository
import com.solutionium.domain.woo.products.GetProductVariationsUseCase

class GetProductVariationsUseCaseImpl(
    private val wooProductRepository: WooProductRepository
) : GetProductVariationsUseCase {
    override suspend fun invoke(productId: Int): Result<List<ProductVariation>, GeneralError> =
        wooProductRepository.getProductVariations(productId)
}