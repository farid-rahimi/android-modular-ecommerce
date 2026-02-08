package com.solutionium.data.network.interceptor

import okhttp3.Interceptor
import javax.inject.Inject

class BearerAuthInterceptor @Inject constructor(
    //private val tokenStore: TokenStore
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        //val token = tokenStore.getToken() // <-- GETTING THE TOKEN

        //val requestBuilder = request.newBuilder()
//        if (token != null) {
//            // Common practice: "Bearer " prefix for JWTs. Adjust if your API differs.
//            requestBuilder.header("Authorization", "Bearer $token")
//        }
        return chain.proceed(request)
    }
}