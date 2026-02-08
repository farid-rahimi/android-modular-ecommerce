package com.solutionium.data.network.services

import com.solutionium.data.network.interceptor.BasicAuthInterceptor
import com.solutionium.data.network.BuildConfig
import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.request.ReviewRequest
import com.solutionium.data.network.response.CartCheckError
import com.solutionium.data.network.response.CartCheckListResponse
import com.solutionium.data.network.response.WooAttributeListResponse
import com.solutionium.data.network.response.WooBrandListResponse
import com.solutionium.data.network.response.WooErrorResponse
import com.solutionium.data.network.response.WooProductDetailsResponse
import com.solutionium.data.network.response.WooProductListResponse
import com.solutionium.data.network.response.WooProductVariationListResponse
import com.solutionium.data.network.response.WooProductVariationResponse
import com.solutionium.data.network.response.WooProductsListResponse
import com.solutionium.data.network.response.WooReviewListResponse
import com.solutionium.data.network.response.WooReviewResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface WooProductService {

    @GET("wp-json/wc/v3/products/{id}")
    suspend fun getProductDetails(
        @Path("id") productId: Int
    ): NetworkResponse<WooProductDetailsResponse, WooErrorResponse>

    //@Headers("Cache-Control: no-cache")
    @GET("wp-json/wc/v3/products/?status=publish")
    suspend fun getProductList(
        @Query("page") page: Int = 1,
        @QueryMap queries: Map<String, String>,
    ): NetworkResponse<WooProductListResponse, WooErrorResponse>

    @GET("wp-json/wc/v3/products/{id}/variations?per_page=100")
    suspend fun getProductVariations(
        @Path("id") productId: Int
    ): NetworkResponse<WooProductVariationListResponse, WooErrorResponse>

    @GET("wp-json/wc/v3/products/brands?orderby=count&order=desc")
    suspend fun getProductBrands(
        @QueryMap queries: Map<String, String> = emptyMap(),
    ): NetworkResponse<WooBrandListResponse, WooErrorResponse>


    @GET("wp-json/wc/v3/products/attributes/{id}/terms")
    suspend fun getAttributeTerms(
        @Path("id") attributeId: Int,
        @QueryMap queries: Map<String, String> = emptyMap(),
    ): NetworkResponse<WooAttributeListResponse, WooErrorResponse>

    @GET("wp-json/wc/v3/products/reviews/")
    suspend fun getProductReviews(
        @Query("page") page: Int = 1,
        @QueryMap queries: Map<String, String> = emptyMap(),
    ): NetworkResponse<WooReviewListResponse, WooErrorResponse>

    @POST("wp-json/wc/v3/products/reviews/")
    suspend fun submitReview(
        @Body review: ReviewRequest,
    ): NetworkResponse<WooReviewResponse, WooErrorResponse>

    @Headers("Cache-Control: no-cache")
    @GET("app/fast-cart.php")
    suspend fun getCartItemUpdate(
        @QueryMap queries: Map<String, String> = emptyMap(),
    ): NetworkResponse<CartCheckListResponse, CartCheckError>

    @Headers("Cache-Control: no-cache")
    @GET("app/product4.php")
    suspend fun getFastProduct(
        @Query("page") page: Int = 1,
        @QueryMap queries: Map<String, String>,
    ): NetworkResponse<WooProductsListResponse, WooErrorResponse>

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
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .build()

    val service = retrofit.create(WooProductService::class.java)
    //val result = service.getProductList()
    //println(result)
}