package com.solutionium.data.network.services

import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.response.WooErrorResponse
import com.solutionium.data.network.response.WooOrderListResponse
import com.solutionium.data.network.response.WooOrderResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WooOrderService {

    @Headers("Cache-Control: no-cache")
    @GET("wp-json/wc/v3/orders/{id}")
    suspend fun getOrderById(
        @Path("id") orderId: Int
    ): NetworkResponse<WooOrderResponse, WooErrorResponse>

    @Headers("Cache-Control: no-cache")
    @GET("wp-json/wc/v3/orders")
    suspend fun getOrders(
        @Query("page") page: Int,
        @QueryMap queries: Map<String, String>
    ): NetworkResponse<WooOrderListResponse, WooErrorResponse>

}