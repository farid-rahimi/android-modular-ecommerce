package com.solutionium.data.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.solutionium.data.network.adapter.NetworkCallAdapterFactory
import com.solutionium.data.network.interceptor.BasicAuthInterceptor
import com.solutionium.data.network.interceptor.BearerAuthInterceptor
import com.solutionium.data.network.interceptor.DynamicCacheInterceptor
import com.solutionium.data.network.services.DigitsService
import com.solutionium.data.network.services.UserService
import com.solutionium.data.network.services.WooCategoryService
import com.solutionium.data.network.services.WooCheckoutOrderService
import com.solutionium.data.network.services.WooProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.Cache
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
annotation class BasicAuthKtorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BearerAuthKtorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthKtorClient

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

    @Provides
    @Singleton
    @BasicAuthKtorClient
    fun provideBasicAuthKtorClient(json: Json): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) { json(json) }
            install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
            install(DefaultRequest) {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                val auth = "${BuildConfig.CONSUMER_KEY}:${BuildConfig.CONSUMER_SECRET}"
                    .encodeToByteArray().let { Base64.encodeToString(it, Base64.NO_WRAP) }
                header(HttpHeaders.Authorization, "Basic $auth")
            }
        }
    }

    @Provides
    @Singleton
    @BearerAuthKtorClient
    fun provideBearerAuthKtorClient(
        json: Json,
        // Inject TokenStore here to get the real token
        // tokenStore: TokenStore 
    ): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) { json(json) }
            install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
            install(DefaultRequest) {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                // val token = tokenStore.getToken()
                // if (token != null) header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    @Provides
    @Singleton
    @NoAuthKtorClient
    fun provideNoAuthKtorClient(json: Json): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) { json(json) }
            install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
            install(DefaultRequest) {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

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
        val cacheDirectory = File(context.cacheDir, "http_cache")
        val cache = Cache(cacheDirectory, CACHE_SIZE_BYTES)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(BasicAuthInterceptor(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET))
            .addNetworkInterceptor(DynamicCacheInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    @BearerAuthInterceptorOkHttpClient
    fun providesBearerOkHttpClient(interceptor: BearerAuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
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
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
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
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    @NoInterceptor
    fun providesOkHttpClientNoAuth(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    @NoInterceptor
    fun providesRetrofit(
        json: Json,
        @NoInterceptor client: OkHttpClient,
        networkCallAdapterFactory: CallAdapter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addCallAdapterFactory(networkCallAdapterFactory)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }


    // Migrated to Ktor
    @Provides
    @Singleton
    fun providesWooProductService(@BasicAuthInterceptorOkHttpClient retrofit: Retrofit): WooProductService {
        return retrofit.create(WooProductService::class.java)
    }


    // Migrated to Ktor
    @Provides
    @Singleton
    fun providesWooCategoryService(@BasicAuthInterceptorOkHttpClient retrofit: Retrofit): WooCategoryService {
        return retrofit.create(WooCategoryService::class.java)
    }

    // Migrated to Ktor
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
    fun providesNetworkCallAdapterFactory(): CallAdapter.Factory {
        return NetworkCallAdapterFactory()
    }
}
