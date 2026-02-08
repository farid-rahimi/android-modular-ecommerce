package com.solutionium.domain.woo.products

import androidx.paging.PagingData
import com.solutionium.data.model.FilterCriterion
import com.solutionium.data.model.ProductListType
import com.solutionium.data.model.ProductThumbnail
import kotlinx.coroutines.flow.Flow

interface GetProductListStreamUseCase {

    operator fun invoke(filters: List<FilterCriterion>): Flow<PagingData<ProductThumbnail>>

}