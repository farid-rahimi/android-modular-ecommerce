package com.solutionium.domain.woo.products.impl

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.Result
import com.solutionium.data.woo.products.WooProductRepository
import com.solutionium.domain.woo.products.SearchProductsUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchProductsUseCaseImpl @Inject constructor(
    private val productRepository: WooProductRepository
) : SearchProductsUseCase {
    override fun invoke(query: String): Flow<Result<List<ProductThumbnail>, GeneralError>> = flow {
        emit(productRepository.getProductsList(mapOf(
            "search" to query.trim(),
            "per_page" to "4"
        )))
    }
}