package com.solutionium.feature.category

import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.solutionium.core.ui.common.DestinationRoute

val GRAPH_CATEGORY_ROUTE = DestinationRoute("category_graph_route")
private const val ROUTE_CATEGORY_SCREEN = "category"

fun NavGraphBuilder.categoryScreen(
    navigateToProductList: (route: DestinationRoute, params: Map<String, String>) -> Unit = { _, _ -> },
    onProductClick: (rootRoute: DestinationRoute, id: Int) -> Unit,
    onNavigateBack: () -> Unit,
    nestedGraphs: NavGraphBuilder.(DestinationRoute) -> Unit,
) {

    navigation(
        route = GRAPH_CATEGORY_ROUTE.route,
        startDestination = "${GRAPH_CATEGORY_ROUTE.route}/$ROUTE_CATEGORY_SCREEN",
    ) {
        composable(
            route = "${GRAPH_CATEGORY_ROUTE.route}/$ROUTE_CATEGORY_SCREEN",
        ) {
            CategoryScreen(
                navigateToProductList = { navigateToProductList(GRAPH_CATEGORY_ROUTE, it) },
                onNavigateBack = {},
                onProductClick = { onProductClick(GRAPH_CATEGORY_ROUTE, it) },
                viewModel =koinViewModel()
            )
        }
        nestedGraphs(GRAPH_CATEGORY_ROUTE)
    }

}