package com.solutionium.feature.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solutionium.core.ui.common.component.OrderStatusFilter
import com.solutionium.shared.data.model.FilterCriterion
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.OrderFilterKey
import com.solutionium.shared.domain.order.GetOrderListPagingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update


class OrderListViewModel(
    private val getOrdersUseCase: GetOrderListPagingUseCase
) : ViewModel() {

    private val _selectedStatus = MutableStateFlow(OrderStatusFilter.ALL)
    val selectedStatus = _selectedStatus.asStateFlow()


    val pagedList: Flow<PagingData<Order>> = _selectedStatus
        .flatMapLatest { statusFilter ->
            // Pass the status key to the use case.
            // If the key is 'all', pass null to fetch all orders.
            val filters: MutableList<FilterCriterion> = emptyList<FilterCriterion>().toMutableList()
            if (statusFilter != OrderStatusFilter.ALL) {
                filters.add(FilterCriterion(OrderFilterKey.STATUS.apiKey, statusFilter.key))
            }
            getOrdersUseCase(filters)
        }
        .cachedIn(viewModelScope)

//    val pagedList: Flow<PagingData<Order>> =
//        getOrdersUseCase(emptyList())
//            .map { pagingData ->
//                val items = mutableListOf<Order>()
//                pagingData.filter { order ->
//                    items.contains(order)
//                        .not()
//                        .also { shouldAdd ->
//                            if (shouldAdd) {
//                                items.add(order)
//                            }
//                        }
//                }
//                //}
//            }.cachedIn(viewModelScope)

    fun onFilterChange(newStatus: OrderStatusFilter) {
        _selectedStatus.update { newStatus }
    }
}