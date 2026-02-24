package com.solutionium.feature.orders

import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.solutionium.sharedui.common.DestinationRoute


private const val ROUTE_ORDERS_SCREEN = "orders"


fun NavGraphBuilder.ordersListScreen(
    rootRoute: DestinationRoute,
    onOrderClick: (rootRoute: DestinationRoute, orderId: Int) -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = "${rootRoute.route}/${ROUTE_ORDERS_SCREEN}",
    ) { _ ->

        OrderListScreen(

            onOrderClick = { onOrderClick(rootRoute, it) },
            onBack = onBack,
            viewModel =koinViewModel()
        )
    }
}

fun NavController.navigateOrdersList(
    rootRoute: DestinationRoute
) {
    navigate("${rootRoute.route}/${ROUTE_ORDERS_SCREEN}")
}
