package com.solutionium.data.network.clients

import com.solutionium.data.network.BasicAuthKtorClient
import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.response.WooErrorResponse
import com.solutionium.data.network.response.WooOrderListResponse
import com.solutionium.data.network.response.WooOrderResponse
import com.solutionium.data.network.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import javax.inject.Inject

class WooOrderClient @Inject constructor(
    @BasicAuthKtorClient private val client: HttpClient
) {

    suspend fun getOrderById(orderId: Int): NetworkResponse<WooOrderResponse, WooErrorResponse> =
        client.safeRequest {
            method = HttpMethod.Companion.Get
            url {
                path("wp-json/wc/v3/orders/")
                appendPathSegments(orderId.toString())
            }
            header(HttpHeaders.CacheControl, "no-cache")
        }

    suspend fun getOrders(
        page: Int,
        queries: Map<String, String>
    ): NetworkResponse<WooOrderListResponse, WooErrorResponse> =
        client.safeRequest {
            method = HttpMethod.Companion.Get
            url {
                path("wp-json/wc/v3/orders")
                parameter("page", page)
                queries.forEach { (key, value) ->
                    parameter(key, value)
                }
            }
            header(HttpHeaders.CacheControl, "no-cache")
        }
}