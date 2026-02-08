package com.solutionium.data.network

import android.content.Context
import android.content.SharedPreferences
import com.solutionium.data.network.adapter.NetworkCallAdapterFactory
import com.solutionium.data.network.interceptor.BasicAuthInterceptor
import com.solutionium.data.network.interceptor.BearerAuthInterceptor
import com.solutionium.data.network.interceptor.DynamicCacheInterceptor
import com.solutionium.data.network.services.DigitsService
import com.solutionium.data.network.services.UserService
import com.solutionium.data.network.services.WooCategoryService
import com.solutionium.data.network.services.WooCheckoutOrderService
import com.solutionium.data.network.services.WooOrderService
import com.solutionium.data.network.services.WooProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import javax.inject.Qualifier

import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicAuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BearerAuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoInterceptor

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB Cache

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    @BasicAuthInterceptorOkHttpClient
    fun providesBasicOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        // Define the cache directory
        val cacheDirectory = File(context.cacheDir, "http_cache")
        val cache = Cache(cacheDirectory, CACHE_SIZE_BYTES)
        return OkHttpClient.Builder()
            .cache(cache) // Add the cache
            .addInterceptor(
                BasicAuthInterceptor(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET)
            )
            .addNetworkInterceptor(DynamicCacheInterceptor(context)) // Optional: To force network or cache
            .build()
    }

    @Provides
    @Singleton
    @BearerAuthInterceptorOkHttpClient
    fun providesBearerOkHttpClient(
        interceptor: BearerAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                interceptor
            )
            .build()
    }



    @Provides
    @Singleton
    @BasicAuthInterceptorOkHttpClient
    fun providesRetrofitBasic(
        json: Json,
        @BasicAuthInterceptorOkHttpClient client: OkHttpClient,
        networkCallAdapterFactory: CallAdapter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addCallAdapterFactory(networkCallAdapterFactory)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    @BearerAuthInterceptorOkHttpClient
    fun providesRetrofitWithBearer(
        json: Json,
        @BearerAuthInterceptorOkHttpClient client: OkHttpClient,
        networkCallAdapterFactory: CallAdapter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addCallAdapterFactory(networkCallAdapterFactory)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    @NoInterceptor // Use the existing qualifier
    fun providesOkHttpClientNoAuth(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Add a logging interceptor ONLY for debug builds
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Log everything
            }
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    @NoInterceptor
    fun providesRetrofit(
        json: Json,
        @NoInterceptor client: OkHttpClient, // <-- INJECT THE NEW CLIENT
        networkCallAdapterFactory: CallAdapter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client) // <-- USE THE CLIENT
            .addCallAdapterFactory(networkCallAdapterFactory)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }



    @Provides
    @Singleton
    fun providesWooProductService(@BasicAuthInterceptorOkHttpClient retrofit: Retrofit): WooProductService {
        return retrofit.create(WooProductService::class.java)
    }

    @Provides
    @Singleton
    fun providesWooCategoryService(@BasicAuthInterceptorOkHttpClient retrofit: Retrofit): WooCategoryService {
        return retrofit.create(WooCategoryService::class.java)
    }

    @Provides
    @Singleton
    fun providesWooCheckoutOrderService(@BasicAuthInterceptorOkHttpClient retrofit: Retrofit): WooCheckoutOrderService {
        return retrofit.create(WooCheckoutOrderService::class.java)
    }


    @Provides
    @Singleton
    fun providesAuthService(@NoInterceptor retrofit: Retrofit): DigitsService {
        return retrofit.create(DigitsService::class.java)
    }

    @Provides
    @Singleton
    fun providesUserService(@NoInterceptor retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun providesOrderService(@BasicAuthInterceptorOkHttpClient retrofit: Retrofit): WooOrderService {
        return retrofit.create(WooOrderService::class.java)
    }

    @Provides
    @Singleton
    fun providesNetworkCallAdapterFactory(): CallAdapter.Factory {
        return NetworkCallAdapterFactory()
    }

}