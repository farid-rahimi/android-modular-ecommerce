package com.solutionium.data.network.clients

import com.solutionium.data.network.response.WooCategoryListResponse
import com.solutionium.data.network.response.WooCategoryResponse
import com.solutionium.data.network.response.WooErrorResponse
import com.solutionium.data.network.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.path

class WooCategoryClient(
    private val client: HttpClient
) {

    suspend fun getCategoryDetails(categoryId: Int) =
        client.safeRequest<WooCategoryResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { appendPathSegments("wp-json", "wc/v3", "products", "categories", categoryId.toString()) }
        }

    suspend fun getCategoryList(
        queries: Map<String, String> = emptyMap(),
    ) = client.safeRequest<WooCategoryListResponse, WooErrorResponse> {
        method = HttpMethod.Get
        url {
            path("wp-json/wc/v3/products/categories/")
            parameter("hide_empty", "true")
            queries.forEach { (key, value) -> parameter(key, value) }
        }
    }
}