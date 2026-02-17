package com.solutionium.data.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
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


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB Cache

    @Provides
    @Singleton
    fun providesJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

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

//    @Provides
//    @Singleton
//    fun providesNetworkCallAdapterFactory(): CallAdapter.Factory {
//        return NetworkCallAdapterFactory()
//    }

}
