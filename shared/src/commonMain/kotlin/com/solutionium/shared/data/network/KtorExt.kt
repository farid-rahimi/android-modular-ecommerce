package com.solutionium.shared.data.network

import com.solutionium.shared.data.network.adapter.NetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.isSuccess

/**
 * Platform-neutral safe request wrapper for KMP.
 */
suspend inline fun <reified S : Any, reified E : Any> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit
): NetworkResponse<S, E> = try {
    val response = this.request { block() }
    if (response.status.isSuccess()) {
        NetworkResponse.Success(response.body<S>())
    } else {
        NetworkResponse.ApiError(response.body<E>(), response.status.value)
    }
} catch (e: Throwable) {
    // In KMP, we use Throwable as the base for network/parsing errors.
    NetworkResponse.NetworkError(e)
}
