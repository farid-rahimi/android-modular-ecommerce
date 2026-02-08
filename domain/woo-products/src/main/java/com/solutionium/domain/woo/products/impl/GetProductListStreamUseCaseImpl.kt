package com.solutionium.domain.woo.products.impl

import androidx.paging.PagingData
import com.solutionium.data.model.FilterCriterion
import com.solutionium.data.model.ProductListType
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.toQueryMap
import com.solutionium.data.woo.products.WooProductRepository
import com.solutionium.domain.woo.products.GetProductListStreamUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetProductListStreamUseCaseImpl @Inject constructor(
    private val wooProductRepository: WooProductRepository
) : GetProductListStreamUseCase {
    override fun invoke(filters: List<FilterCriterion>): Flow<PagingData<ProductThumbnail>> =
        wooProductRepository.getProductListStream(filters.toQueryMap())
}