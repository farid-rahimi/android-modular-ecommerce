package com.solutionium.domain.woo.products.impl

import androidx.paging.PagingData
import com.solutionium.shared.data.model.FilterCriterion
import com.solutionium.shared.data.model.ProductThumbnail
import com.solutionium.shared.data.model.toQueryMap
import com.solutionium.data.woo.products.WooProductRepository
import com.solutionium.domain.woo.products.GetProductListStreamUseCase
import kotlinx.coroutines.flow.Flow

internal class GetProductListStreamUseCaseImpl(
    private val wooProductRepository: WooProductRepository
) : GetProductListStreamUseCase {
    override fun invoke(filters: List<FilterCriterion>): Flow<PagingData<ProductThumbnail>> =
        wooProductRepository.getProductListStream(filters.toQueryMap())
}