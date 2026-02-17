package com.solutionium.data.network.services

import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.request.OrderRequest
import com.solutionium.data.network.response.PaymentGatewayListResponse
import com.solutionium.data.network.response.ShippingMethodsResponse
import com.solutionium.data.network.response.WooCouponListResponse
import com.solutionium.data.network.response.WooErrorResponse
import com.solutionium.data.network.response.WooOrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WooCheckoutOrderService {

    @GET("wp-json/wc/v3/shipping/zones/1/methods")
    suspend fun getShippingMethods(): NetworkResponse<ShippingMethodsResponse, WooErrorResponse>

    @GET("wp-json/wc/v3/payment_gateways")
    suspend fun getPaymentGateways(): NetworkResponse<PaymentGatewayListResponse, WooErrorResponse>

    @POST("wp-json/wc/v3/orders")
    suspend fun createOrder(
        @Body order: OrderRequest
    ): NetworkResponse<WooOrderResponse, WooErrorResponse>

    @POST("wp-json/wc/v3/orders/{id}")
    suspend fun updateOrder(
        @Path("id") orderId: Int,
        @Body order: OrderRequest
    ): NetworkResponse<WooOrderResponse, WooErrorResponse>

    @Headers("Cache-Control: no-cache")
    @GET("/wp-json/wc/v3/coupons")
    suspend fun getCoupons(
        @Query("code") code: String
    ): NetworkResponse<WooCouponListResponse, WooErrorResponse>
}


