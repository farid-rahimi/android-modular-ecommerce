package com.solutionium.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.solutionium.sharedui.common.DestinationRoute
import com.solutionium.shared.data.model.StoryItem

val GRAPH_HOME_ROUTE = DestinationRoute("home_graph_route")
private const val ROUTE_HOME_SCREEN = "home"


fun NavGraphBuilder.homeScreen(
    onProductClick: (rootRoute: DestinationRoute, id: Int) -> Unit,
    onShowProductListClick: (rootRoute: DestinationRoute, params: Map<String, String>) -> Unit,
    onStoryClick: (StoryItem) -> Unit,
    nestedGraphs: NavGraphBuilder.(DestinationRoute) -> Unit,
    homeViewModel: HomeViewModel
) {
    navigation(
        route = GRAPH_HOME_ROUTE.route,
//        deepLinks = listOf(navDeepLink {
//            uriPattern = "https://qeshminora.com/product/{productSlug}"
//            //action = Intent.ACTION_VIEW
//        }),
        startDestination = "${GRAPH_HOME_ROUTE.route}/$ROUTE_HOME_SCREEN",
    ) {
        composable(
            "${GRAPH_HOME_ROUTE.route}/$ROUTE_HOME_SCREEN",
        ) { navBackStack ->
            //val productSlug = navBackStack.arguments?.getString("productSlug")
            HomeScreen(
                onProductClick = { onProductClick(GRAPH_HOME_ROUTE, it) },
                navigateToProductList = { params -> onShowProductListClick(GRAPH_HOME_ROUTE, params) },
                onStoryClick = onStoryClick,
                viewModel = homeViewModel
            )
        }

        nestedGraphs(GRAPH_HOME_ROUTE)
    }
}

fun NavController.navigateToHome() {
    navigate(GRAPH_HOME_ROUTE.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}