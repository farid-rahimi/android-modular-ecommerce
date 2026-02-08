package com.solutionium.domain.review

import androidx.paging.PagingData
import com.solutionium.data.model.FilterCriterion
import com.solutionium.data.model.Order
import com.solutionium.data.model.Review
import com.solutionium.data.model.toQueryMap
import com.solutionium.data.woo.products.WooProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewListPagingUseCaseImpl @Inject constructor(

    private val productRepository: WooProductRepository

) : GetReviewListPagingUseCase {
    override fun invoke(filters: List<FilterCriterion>): Flow<PagingData<Review>> =
        productRepository.getReviewListStream(filters.toQueryMap())
}