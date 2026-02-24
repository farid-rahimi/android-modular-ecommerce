package com.solutionium.feature.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.solutionium.sharedui.common.component.OrderStatusFilter
import com.solutionium.sharedui.common.component.OrderSummaryCard
import com.solutionium.sharedui.common.component.OrderSummaryCardPlaceholder
import com.solutionium.shared.data.model.Order

@Composable
fun OrderListScreen(

    onOrderClick: (orderId: Int) -> Unit,
    onBack: () -> Unit,
    viewModel: OrderListViewModel
) {
    val orders: LazyPagingItems<Order> = viewModel.pagedList.collectAsLazyPagingItems()
    val selectedStatus by viewModel.selectedStatus.collectAsStateWithLifecycle()

    val isRefreshing = orders.loadState.refresh is LoadState.Loading


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_orders)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            OrderFilterChips(
                selectedStatus = selectedStatus,
                onStatusSelected = viewModel::onFilterChange
            )
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { orders.refresh() } // <-- Call refresh on the Paging items
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Check the refresh state for the initial load
                    if (orders.loadState.refresh is LoadState.Loading) {
                        items(5) { // Display 5 shimmer placeholders
                            OrderSummaryCardPlaceholder()
                        }
                    }

                    // Display the actual loaded items
                    items(orders.itemCount) { index ->
                        orders[index]?.let { order ->
                            OrderSummaryCard(
                                order = order,
                                onClick = { onOrderClick(order.id) }
                            )
                        }
                    }

                    // Check the append state for pagination loading
                    if (orders.loadState.append is LoadState.Loading) {
                        item { // Display one placeholder at the bottom
                            OrderSummaryCardPlaceholder()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderFilterChips(
    selectedStatus: OrderStatusFilter,
    onStatusSelected: (OrderStatusFilter) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(OrderStatusFilter.entries.toTypedArray()) { status ->
            FilterChip(
                colors = FilterChipDefaults.filterChipColors()
                    .copy(containerColor = colorResource(status.colorId).copy(alpha = 0.2f)),
                selected = status == selectedStatus,
                onClick = { onStatusSelected(status) },
                label = { Text(stringResource(status.titleResourceId)) }
            )
        }
    }
}
