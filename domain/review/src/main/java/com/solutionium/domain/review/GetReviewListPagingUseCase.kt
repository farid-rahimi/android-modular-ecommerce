package com.solutionium.domain.review

import androidx.paging.PagingData
import com.solutionium.shared.data.model.FilterCriterion
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.Review
import kotlinx.coroutines.flow.Flow

interface GetReviewListPagingUseCase {

    operator fun invoke(filters: List<FilterCriterion>): Flow<PagingData<Review>>

}