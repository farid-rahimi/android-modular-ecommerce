package com.solutionium.shared.domain.products.impl

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.products.WooProductRepository
import com.solutionium.shared.domain.products.SearchProductsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchProductsUseCaseImpl(
    private val productRepository: WooProductRepository
) : SearchProductsUseCase {
    override fun invoke(query: String): Flow<Result<List<ProductThumbnail>, GeneralError>> = flow {
        emit(productRepository.getProductsList(mapOf(
            "search" to query.trim(),
            "per_page" to "4"
        )))
    }
}