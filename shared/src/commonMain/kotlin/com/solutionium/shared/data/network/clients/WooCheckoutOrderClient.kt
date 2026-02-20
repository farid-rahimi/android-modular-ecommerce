package com.solutionium.shared.data.network.clients

import com.solutionium.shared.data.network.request.OrderRequest
import com.solutionium.shared.data.network.response.PaymentGatewayListResponse
import com.solutionium.shared.data.network.response.ShippingMethodsResponse
import com.solutionium.shared.data.network.response.WooCouponListResponse
import com.solutionium.shared.data.network.response.WooErrorResponse
import com.solutionium.shared.data.network.response.WooOrderResponse
import com.solutionium.shared.data.network.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.path

class WooCheckoutOrderClient(
    private val client: HttpClient
) {

    suspend fun getShippingMethods() =
        client.safeRequest<ShippingMethodsResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { path("wp-json/wc/v3/shipping/zones/1/methods") }
        }

    suspend fun getPaymentGateways() =
        client.safeRequest<PaymentGatewayListResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { path("wp-json/wc/v3/payment_gateways") }
        }

    suspend fun createOrder(order: OrderRequest) =
        client.safeRequest<WooOrderResponse, WooErrorResponse> {
            method = HttpMethod.Post
            url { path("wp-json/wc/v3/orders") }
            setBody(order)
        }

    suspend fun updateOrder(orderId: Int, order: OrderRequest) =
        client.safeRequest<WooOrderResponse, WooErrorResponse> {
            method = HttpMethod.Post
            url {
                path("wp-json/wc/v3/orders/")
                appendPathSegments(orderId.toString())
            }
            setBody(order)
        }

    suspend fun getCoupons(code: String) =
        client.safeRequest<WooCouponListResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url {
                path("wp-json/wc/v3/coupons")
                parameter("code", code)
            }
            header(HttpHeaders.CacheControl, "no-cache")
        }
}