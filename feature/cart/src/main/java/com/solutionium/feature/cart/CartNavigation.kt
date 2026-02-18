package com.solutionium.feature.cart

import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.solutionium.core.ui.common.DestinationRoute


val GRAPH_CART_ROUTE = DestinationRoute("cart_graph_route")
private const val ROUTE_CART_SCREEN = "cart"

fun NavGraphBuilder.cartScreen(
    onCheckoutClick: (rootRoute: DestinationRoute) -> Unit,
    onProductClick: (rootRoute: DestinationRoute, id: Int) -> Unit,
    onNavigateAccount: () -> Unit,
    nestedGraphs: NavGraphBuilder.(DestinationRoute) -> Unit,
) {

    navigation(
        route = GRAPH_CART_ROUTE.route,
        startDestination = "${GRAPH_CART_ROUTE.route}/$ROUTE_CART_SCREEN",
    ) {
        composable(
            route = "${GRAPH_CART_ROUTE.route}/$ROUTE_CART_SCREEN",
        ) {
            CartScreen(
                onCheckoutClick = { onCheckoutClick(GRAPH_CART_ROUTE)},
                onProductClick = { onProductClick(GRAPH_CART_ROUTE, it) },
                onNavigateToAccount = onNavigateAccount,
                viewModel =koinViewModel()
            )
        }
        nestedGraphs(GRAPH_CART_ROUTE)
    }
}
