package com.solutionium.feature.review

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solutionium.core.ui.common.DestinationRoute


fun NavGraphBuilder.reviewScreen(
    rootRoute: DestinationRoute,
    onBackClick: () -> Unit,
) {
    composable(
        route = "${rootRoute.route}/reviews?id={productId}&catIds={categoryIds}",
        arguments = listOf(
            navArgument("productId") {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument("categoryIds") {
                type = NavType.StringType
                nullable = true // Make it nullable in case it's not passed
            }
        ),

        ) {
        ReviewListScreen(
            viewModel = hiltViewModel(),
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateReviews(
    rootRoute: DestinationRoute,
    productId: Int,
    categoryIds: List<Int>
) {
    val categoryIdsArg = categoryIds.joinToString(",")
    navigate("${rootRoute.route}/reviews?id=$productId&catIds=$categoryIdsArg")
}
