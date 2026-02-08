package com.solutionium.data.network.services

import com.solutionium.data.network.interceptor.BasicAuthInterceptor
import com.solutionium.data.network.BuildConfig
import com.solutionium.data.network.adapter.NetworkCallAdapterFactory
import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.response.WooCategoryListResponse
import com.solutionium.data.network.response.WooCategoryResponse
import com.solutionium.data.network.response.WooErrorResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WooCategoryService {

    @GET("wp-json/wc/v3/products/categories/{id}")
    suspend fun getCategoryDetails(
        @Path("id") categoryId: Int
    ): NetworkResponse<WooCategoryResponse, WooErrorResponse>

    @GET("wp-json/wc/v3/products/categories/?hide_empty=true")
    suspend fun getCategoryList(
        @QueryMap queries: Map<String, String> = emptyMap(),
    ): NetworkResponse<WooCategoryListResponse, WooErrorResponse>

}


suspend fun main() {
    val json = Json { ignoreUnknownKeys = true }
    val client = OkHttpClient.Builder()
        .addInterceptor(
            BasicAuthInterceptor(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET)
        )
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addCallAdapterFactory(NetworkCallAdapterFactory())
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()

    val service = retrofit.create(WooCategoryService::class.java)
    val result = service.getCategoryList()
    println(result)
}