package com.solutionium.data.network.interceptor

import okhttp3.Credentials
import okhttp3.Interceptor

class BasicAuthInterceptor(ck: String, cs: String): Interceptor {
    private var credentials: String = Credentials.basic(ck, cs)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}

