package com.solutionium.data.network.clients

import com.solutionium.data.network.NoAuthKtorClient
import com.solutionium.data.network.request.DigitsRegisterRequest
import com.solutionium.data.network.response.DigitsErrorResponse
import com.solutionium.data.network.response.DigitsLoginRegisterResponse
import com.solutionium.data.network.response.DigitsOtpErrorResponse
import com.solutionium.data.network.response.DigitsOtpResponse
import com.solutionium.data.network.response.DigitsSimpleResponse
import com.solutionium.data.network.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.path
import javax.inject.Inject

class DigitsClient @Inject constructor(
    @NoAuthKtorClient private val client: HttpClient
) {

    suspend fun sendOTP(params: Map<String, String>) =
        client.safeRequest<DigitsOtpResponse, DigitsOtpErrorResponse> {
            method = HttpMethod.Companion.Post
            url {
                path("wp-json/digits/v1/send_otp")
                params.forEach { (key, value) -> parameter(key, value) }
            }
        }

    suspend fun loginUser(user: String, password: String) =
        client.safeRequest<DigitsLoginRegisterResponse, DigitsErrorResponse> {
            method = HttpMethod.Companion.Post
            url { path("wp-json/digits/v1/login_user") }
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("user", user)
                        append("password", password)
                    }
                )
            )
        }

    suspend fun verifyOTP(otp: String, phone: String) =
        client.safeRequest<DigitsLoginRegisterResponse, DigitsErrorResponse> {
            method = HttpMethod.Companion.Post
            url {
                path("wp-json/digits/v1/verify_otp")
                parameter("otp", otp)
                parameter("phone", phone)
            }
        }

    suspend fun resendOTP(phone: String) =
        client.safeRequest<DigitsLoginRegisterResponse, DigitsErrorResponse> {
            method = HttpMethod.Companion.Post
            url {
                path("wp-json/digits/v1/resend_otp")
                parameter("phone", phone)
            }
        }

    suspend fun registerUser(body: DigitsRegisterRequest) =
        client.safeRequest<DigitsLoginRegisterResponse, DigitsErrorResponse> {
            method = HttpMethod.Companion.Post
            url { path("wp-json/digits/v1/register_user") }
            setBody(body)
        }

    suspend fun oneClick(params: Map<String, String>) =
        client.safeRequest<DigitsLoginRegisterResponse, DigitsErrorResponse> {
            method = HttpMethod.Companion.Post
            url {
                path("wp-json/digits/v1/one_click")
                params.forEach { (key, value) -> parameter(key, value) }
            }
        }

    suspend fun logout(token: String) =
        client.safeRequest<DigitsSimpleResponse, DigitsSimpleResponse> {
            method = HttpMethod.Companion.Post
            url { path("wp-json/digits/v1/logout") }
            header(HttpHeaders.Authorization, token)
        }
}