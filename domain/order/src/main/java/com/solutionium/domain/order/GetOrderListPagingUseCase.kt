package com.solutionium.domain.order

import androidx.paging.PagingData
import com.solutionium.shared.data.model.FilterCriterion
import com.solutionium.shared.data.model.Order
import kotlinx.coroutines.flow.Flow

interface GetOrderListPagingUseCase {

    operator fun invoke(filters: List<FilterCriterion>): Flow<PagingData<Order>>

}