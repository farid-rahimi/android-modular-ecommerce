package com.solutionium.shared.domain.products

import androidx.paging.PagingData
import com.solutionium.shared.data.model.FilterCriterion
import com.solutionium.shared.data.model.ProductThumbnail
import kotlinx.coroutines.flow.Flow

interface GetProductListStreamUseCase {

    operator fun invoke(filters: List<FilterCriterion>): Flow<PagingData<ProductThumbnail>>

}