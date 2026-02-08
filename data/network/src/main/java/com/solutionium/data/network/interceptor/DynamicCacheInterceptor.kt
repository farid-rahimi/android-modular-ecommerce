package com.solutionium.data.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class DynamicCacheInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalResponse: Response

        if (isNetworkAvailable(context)) {
            // Online: Prefer fresh data, cache for a short period (e.g., 1 minute)
            val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.MINUTES)
                .build()
            request = request.newBuilder().cacheControl(cacheControl).build()
            originalResponse = chain.proceed(request)

            // Modify the response's Cache-Control to ensure it's cached by OkHttp as intended
            return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 300) // Cache for 60 seconds
                .removeHeader("Pragma") // Pragma can interfere with caching
                .build()
        } else {
            // Offline: Force cache, allow stale data for a longer period (e.g., 7 days)
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(7, TimeUnit.DAYS)
                .build()
            request = request.newBuilder().cacheControl(cacheControl).build()
            originalResponse = chain.proceed(request) // This might throw 504 if not cached

            // If you reach here, it means it was served from cache (or failed with 504)
            return originalResponse
        }
    }
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}