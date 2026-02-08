package com.solutionium.feature.product.list

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solutionium.core.ui.common.DestinationRoute
import com.solutionium.data.model.FilterCriterion
import com.solutionium.data.model.PRODUCT_ARG_ATTRIBUTE
import com.solutionium.data.model.PRODUCT_ARG_ATTRIBUTE_TERM
import com.solutionium.data.model.PRODUCT_ARG_BRAND_ID
import com.solutionium.data.model.PRODUCT_ARG_CATEGORY
import com.solutionium.data.model.PRODUCT_ARG_FEATURED
import com.solutionium.data.model.PRODUCT_ARG_IDS
import com.solutionium.data.model.PRODUCT_ARG_ON_SALE
import com.solutionium.data.model.PRODUCT_ARG_SEARCH
import com.solutionium.data.model.PRODUCT_ARG_TAG
import com.solutionium.data.model.PRODUCT_ARG_TITLE
import com.solutionium.data.model.ProductFilterKey
import com.solutionium.data.model.ProductListType

internal const val PRODUCT_LIST_ROUTE = "productList"
internal const val PRODUCT_ARG_LIST_TYPE = "listType"


fun NavGraphBuilder.productListScreen(
    rootRoute: DestinationRoute,
    onProductClick: (rootRoute: DestinationRoute, id: Int) -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = "${rootRoute.route}/${PRODUCT_LIST_ROUTE}" +
                "?${PRODUCT_ARG_BRAND_ID}={${PRODUCT_ARG_BRAND_ID}}" +
                "&${PRODUCT_ARG_ATTRIBUTE}={${PRODUCT_ARG_ATTRIBUTE}}" +
                "&${PRODUCT_ARG_ATTRIBUTE_TERM}={${PRODUCT_ARG_ATTRIBUTE_TERM}}" +
                "&${PRODUCT_ARG_IDS}={${PRODUCT_ARG_IDS}}" +
                "&${PRODUCT_ARG_TITLE}={${PRODUCT_ARG_TITLE}}" +
                "&${PRODUCT_ARG_CATEGORY}={${PRODUCT_ARG_CATEGORY}}" +
                "&${PRODUCT_ARG_TAG}={${PRODUCT_ARG_TAG}}" +
                "&${PRODUCT_ARG_SEARCH}={${PRODUCT_ARG_SEARCH}}" +
                "&${PRODUCT_ARG_FEATURED}={${PRODUCT_ARG_FEATURED}}" +
                "&${PRODUCT_ARG_ON_SALE}={${PRODUCT_ARG_ON_SALE}}"
        ,
        arguments = listOf(
            navArgument(PRODUCT_ARG_BRAND_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_ATTRIBUTE) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_ATTRIBUTE_TERM) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_IDS) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_TITLE) {
                type = NavType.StringType
                nullable = true // Make it optional
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_CATEGORY) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_TAG) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_SEARCH) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_FEATURED) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(PRODUCT_ARG_ON_SALE) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        )
    ) { navBack ->
        ProductListScreen(
            onProductClick = { onProductClick(rootRoute, it) },
            onBack = onBack,
        )
    }
}

//fun NavController.navigateProductList(
//    rootRoute: DestinationRoute,
//    productType: ProductListType,
//) {
//    val route = rootRoute.route
//
//    navigate(route)
//}

fun NavController.navigateProductList(
    rootRoute: DestinationRoute,
    params: Map<String, String>
) {
    var route = "${rootRoute.route}/${PRODUCT_LIST_ROUTE}"
    val queryParams = mutableListOf<String>()

    params.map { (key, value) -> queryParams.add("$key=$value") }

    if (queryParams.isNotEmpty()) {
        route += "?" + queryParams.joinToString("&")
    }
    navigate(route)
}



internal class ProductListFilters(
    private val filters: MutableList<FilterCriterion> = mutableListOf<FilterCriterion>(),
) {
    fun buildFilterCriteria(savedStateHandle: SavedStateHandle) : MutableList<FilterCriterion> {
        val brandId: String? = savedStateHandle.get<String>(PRODUCT_ARG_BRAND_ID)
        brandId?.let { filters.add(FilterCriterion(ProductFilterKey.BRAND_ID.apiKey, it)) }
        val attribute: String? = savedStateHandle.get<String>(PRODUCT_ARG_ATTRIBUTE)
        val attributeTerm: String? = savedStateHandle.get<String>(PRODUCT_ARG_ATTRIBUTE_TERM)
        if (attribute != null && attributeTerm != null) {
            filters.add(FilterCriterion(ProductFilterKey.ATTRIBUTE.apiKey, attribute))
            filters.add(FilterCriterion(ProductFilterKey.ATTRIBUTE_TERM.apiKey, attributeTerm))
        }
        val productIds: String? = savedStateHandle.get<String>(PRODUCT_ARG_IDS)
        productIds?.let {
            if (it.isNotEmpty()) {
                filters.add(FilterCriterion(ProductFilterKey.INCLUDE_IDS.apiKey, it))
            }
        }
        val categoryId: String? = savedStateHandle.get<String>(PRODUCT_ARG_CATEGORY)
        categoryId?.let { filters.add(FilterCriterion(ProductFilterKey.CATEGORY_ID.apiKey, it)) }
        val tagId: String? = savedStateHandle.get<String>(PRODUCT_ARG_TAG)
        tagId?.let { filters.add(FilterCriterion(ProductFilterKey.TAG.apiKey, it)) }

        val search: String? = savedStateHandle.get<String>(PRODUCT_ARG_SEARCH)
        search?.let { filters.add(FilterCriterion(ProductFilterKey.SEARCH.apiKey, it)) }

        val featured: String? = savedStateHandle.get<String>(PRODUCT_ARG_FEATURED)
        featured?.let { filters.add(FilterCriterion(ProductFilterKey.FEATURED.apiKey, it)) }

        val onSale: String? = savedStateHandle.get<String>(PRODUCT_ARG_ON_SALE)
        onSale?.let { filters.add(FilterCriterion(ProductFilterKey.ON_SALE.apiKey, it)) }

        return filters
    }

}


