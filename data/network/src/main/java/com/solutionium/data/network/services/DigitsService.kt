package com.solutionium.data.network.services

import com.solutionium.data.network.BuildConfig
import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.request.DigitsRegisterRequest
import com.solutionium.data.network.response.DigitsErrorResponse
import com.solutionium.data.network.response.DigitsLoginRegisterResponse
import com.solutionium.data.network.response.DigitsOtpErrorResponse
import com.solutionium.data.network.response.DigitsOtpResponse
import com.solutionium.data.network.response.DigitsSimpleResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface DigitsService {

    @POST("wp-json/digits/v1/send_otp")
    suspend fun sendOTP(@QueryMap params: Map<String, String>): NetworkResponse<DigitsOtpResponse, DigitsOtpErrorResponse>

    @Multipart
    @POST("wp-json/digits/v1/login_user")
    suspend fun loginUser(
        @Part("user") user: RequestBody, // 4. Use @Part for each form field
        @Part("password") password: RequestBody
    ): NetworkResponse<DigitsLoginRegisterResponse, DigitsErrorResponse>




    @POST("wp-json/digits/v1/verify_otp")
    suspend fun verifyOTP(@Query("otp") otp: String, @Query("phone") phone: String): NetworkResponse<DigitsLoginRegisterResponse, DigitsErrorResponse>

    @POST("wp-json/digits/v1/resend_otp")
    suspend fun resendOTP(@Query("phone") phone: String): NetworkResponse<DigitsLoginRegisterResponse, DigitsErrorResponse>

    @POST("wp-json/digits/v1/register_user")
    suspend fun registerUser(@Body body: DigitsRegisterRequest): NetworkResponse<DigitsLoginRegisterResponse, DigitsErrorResponse>

    @POST("wp-json/digits/v1/one_click")
    suspend fun oneClick(@QueryMap params: Map<String, String>): NetworkResponse<DigitsLoginRegisterResponse, DigitsErrorResponse>

    @POST("wp-json/digits/v1/logout")
    suspend fun logout(@Header("Authorization") token: String): NetworkResponse<DigitsSimpleResponse, DigitsSimpleResponse>



}

suspend fun main() {
    val json = Json { ignoreUnknownKeys = true }
//    val client = OkHttpClient.Builder()
//        .addInterceptor(
//            BasicAuthInterceptor(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET)
//        )
//        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        //.client(client)
        .addConverterFactory(
            json.asConverterFactory("multipart/form-data".toMediaType())
        )
        .build()

    val service = retrofit.create(DigitsService::class.java)
    val result = service.loginUser("m_hameedat".toRequestBody(MultipartBody.FORM), "569254724@H".toRequestBody(MultipartBody.FORM))
    println(result)
}