package com.solutionium.domain.review

import androidx.paging.PagingData
import com.solutionium.data.model.FilterCriterion
import com.solutionium.data.model.Review
import com.solutionium.data.model.toQueryMap
import com.solutionium.data.woo.products.WooProductRepository
import kotlinx.coroutines.flow.Flow

class GetReviewListPagingUseCaseImpl(

    private val productRepository: WooProductRepository

) : GetReviewListPagingUseCase {
    override fun invoke(filters: List<FilterCriterion>): Flow<PagingData<Review>> =
        productRepository.getReviewListStream(filters.toQueryMap())
}