package com.solutionium.data.network.clients

import com.solutionium.data.network.request.EditUserRequest
import com.solutionium.data.network.response.AppConfigResponse
import com.solutionium.data.network.response.PrivacyPolicyResponse
import com.solutionium.data.network.response.WooErrorResponse
import com.solutionium.data.network.response.WooUserWalletResponse
import com.solutionium.data.network.response.WooWalletConfigResponse
import com.solutionium.data.network.response.WpUserResponse
import com.solutionium.data.network.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.path

class UserClient(
    private val client: HttpClient
) {

    suspend fun getMe(token: String) =
        client.safeRequest<WpUserResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { path("wp-json/wp/v2/users/me") }
            header(HttpHeaders.Authorization, token)
        }

    suspend fun updateUser(userId: String, userData: EditUserRequest, token: String) =
        client.safeRequest<WpUserResponse, WooErrorResponse> {
            method = HttpMethod.Patch
            url {
                path("wp-json/wp/v2/users/")
                appendPathSegments(userId)
            }
            setBody(userData)
            header(HttpHeaders.Authorization, token)
        }

    suspend fun getUserWallet(token: String) =
        client.safeRequest<WooUserWalletResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { path("wp-json/wallet/v1/user") }
            header(HttpHeaders.Authorization, token)
        }

    suspend fun getWalletConfig() =
        client.safeRequest<WooWalletConfigResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { path("wp-json/wallet/v1/settings") }
        }

    suspend fun getAppConfig() =
        client.safeRequest<AppConfigResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { path("app/config.php") }
            header(HttpHeaders.CacheControl, "no-cache")
        }

    suspend fun getPrivacyPolicy() =
        client.safeRequest<PrivacyPolicyResponse, WooErrorResponse> {
            method = HttpMethod.Get
            url { path("app/privacy-policy.php") }
            header(HttpHeaders.CacheControl, "no-cache")
        }
}