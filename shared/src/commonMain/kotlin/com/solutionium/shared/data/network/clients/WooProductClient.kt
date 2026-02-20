package com.solutionium.shared.data.network.clients

import com.solutionium.shared.data.network.request.ReviewRequest
import com.solutionium.shared.data.network.response.CartCheckError
import com.solutionium.shared.data.network.response.CartCheckListResponse
import com.solutionium.shared.data.network.response.WooAttributeListResponse
import com.solutionium.shared.data.network.response.WooBrandListResponse
import com.solutionium.shared.data.network.response.WooErrorResponse
import com.solutionium.shared.data.network.response.WooProductDetailsResponse
import com.solutionium.shared.data.network.response.WooProductListResponse
import com.solutionium.shared.data.network.response.WooProductVariationListResponse
import com.solutionium.shared.data.network.response.WooProductsListResponse
import com.solutionium.shared.data.network.response.WooReviewListResponse
import com.solutionium.shared.data.network.response.WooReviewResponse
import com.solutionium.shared.data.network.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.path


 class WooProductClient(
    private val client: HttpClient
) {

     suspend fun getProductDetails(productId: Int) =
        client.safeRequest<WooProductDetailsResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { appendPathSegments("wp-json", "wc/v3", "products", productId.toString()) }
        }

    suspend fun getProductList(
        page: Int = 1,
        queries: Map<String, String> = emptyMap(),
    ) = client.safeRequest<WooProductListResponse, WooErrorResponse> {
        method = HttpMethod.Get
        url {
            path("wp-json/wc/v3/products/")
            parameter("status", "publish")
            parameter("page", page)
            queries.forEach { (key, value) -> parameter(key, value) }
        }
    }

    suspend fun getProductVariations(productId: Int) =
        client.safeRequest<WooProductVariationListResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url {
                appendPathSegments("wp-json", "wc/v3", "products", productId.toString(), "variations")
                parameter("per_page", 100)
            }
        }

    suspend fun getProductBrands(
        queries: Map<String, String> = emptyMap(),
    ) = client.safeRequest<WooBrandListResponse, WooErrorResponse> {
        method = HttpMethod.Get
        url {
            path("wp-json/wc/v3/products/brands")
            parameter("orderby", "count")
            parameter("order", "desc")
            queries.forEach { (key, value) -> parameter(key, value) }
        }
    }

    suspend fun getAttributeTerms(
        attributeId: Int,
        queries: Map<String, String> = emptyMap(),
    ) = client.safeRequest<WooAttributeListResponse, WooErrorResponse> {
        method = HttpMethod.Get
        url {
            appendPathSegments("wp-json", "wc/v3", "products", "attributes", attributeId.toString(), "terms")
            queries.forEach { (key, value) -> parameter(key, value) }
        }
    }

    suspend fun getProductReviews(
        page: Int = 1,
        queries: Map<String, String> = emptyMap(),
    ) = client.safeRequest<WooReviewListResponse, WooErrorResponse> {
        method = HttpMethod.Get
        url {
            path("wp-json/wc/v3/products/reviews/")
            parameter("page", page)
            queries.forEach { (key, value) -> parameter(key, value) }
        }
    }

    suspend fun submitReview(review: ReviewRequest) =
        client.safeRequest<WooReviewResponse, WooErrorResponse> {
            method = HttpMethod.Post
            url { path("wp-json/wc/v3/products/reviews/") }
            setBody(review)
        }

    suspend fun getCartItemUpdate(
        queries: Map<String, String> = emptyMap(),
    ) = client.safeRequest<CartCheckListResponse, CartCheckError> {
        method = HttpMethod.Get
        url {
            path("app/fast-cart.php")
            queries.forEach { (key, value) -> parameter(key, value) }
        }
    }

    suspend fun getFastProduct(
        page: Int = 1,
        queries: Map<String, String>,
    ) = client.safeRequest<WooProductsListResponse, WooErrorResponse> {
        method = HttpMethod.Get
        url {
            path("app/product4.php")
            parameter("page", page)
            queries.forEach { (key, value) -> parameter(key, value) }
        }
    }
}