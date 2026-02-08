package com.solutionium.data.network.services

import com.solutionium.data.network.adapter.NetworkResponse
import com.solutionium.data.network.request.EditUserRequest
import com.solutionium.data.network.response.AppConfigResponse
import com.solutionium.data.network.response.CartCheckError
import com.solutionium.data.network.response.PrivacyPolicyResponse
import com.solutionium.data.network.response.WooErrorResponse
import com.solutionium.data.network.response.WooUserWalletResponse
import com.solutionium.data.network.response.WooWalletConfigResponse
import com.solutionium.data.network.response.WpUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface UserService {

    @GET("wp-json/wp/v2/users/me")
    suspend fun getMe(@Header("Authorization") token: String): NetworkResponse<WpUserResponse, WooErrorResponse>

    @PATCH("wp-json/wp/v2/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: String,
        @Body userData: EditUserRequest,
        @Header("Authorization") token: String
    ): NetworkResponse<WpUserResponse, WooErrorResponse>


    @GET("wp-json/wallet/v1/user")
    suspend fun getUserWallet(@Header("Authorization") token: String): NetworkResponse<WooUserWalletResponse, WooErrorResponse>

    @GET("wp-json/wallet/v1/settings")
    suspend fun getWalletConfig(): NetworkResponse<WooWalletConfigResponse, WooErrorResponse>

    @Headers("Cache-Control: no-cache")
    @GET("app/config.php")
    suspend fun getAppConfig(): NetworkResponse<AppConfigResponse, WooErrorResponse>

    @Headers("Cache-Control: no-cache")
    @GET("app/privacy-policy.php")
    suspend fun getPrivacyPolicy(): NetworkResponse<PrivacyPolicyResponse, WooErrorResponse>


}