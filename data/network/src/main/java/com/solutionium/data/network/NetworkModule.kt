package com.solutionium.data.network

import android.content.Context
import android.util.Base64
import com.solutionium.data.network.clients.DigitsClient
import com.solutionium.data.network.clients.UserClient
import com.solutionium.data.network.clients.WooCategoryClient
import com.solutionium.data.network.clients.WooCheckoutOrderClient
import com.solutionium.data.network.clients.WooOrderClient
import com.solutionium.data.network.clients.WooProductClient
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
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getNetworkDataModules() = setOf(networkModule)

val networkModule = module {

    single {
        Json { ignoreUnknownKeys = true }
    }

    // --- HTTP CLIENTS ---

    single(named("BasicAuthKtorClient")) {
        HttpClient(Android) {
            install(ContentNegotiation) { json(get()) }
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

    single(named("BearerAuthKtorClient")) {
        HttpClient(Android) {
            install(ContentNegotiation) { json(get()) }
            install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
            install(DefaultRequest) {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                // Inject TokenStore here if needed for dynamic Bearer tokens
            }
        }
    }

    single(named("NoAuthKtorClient")) {
        HttpClient(Android) {
            install(ContentNegotiation) { json(get()) }
            install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
            install(DefaultRequest) {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    // --- API CLIENTS (Mapping them to the correct HttpClient) ---

    single { WooProductClient(get(named("BasicAuthKtorClient"))) }
    single { WooCategoryClient(get(named("BasicAuthKtorClient"))) }
    single { WooCheckoutOrderClient(get(named("BasicAuthKtorClient"))) }
    single { WooOrderClient(get(named("BasicAuthKtorClient"))) }
    
    single { DigitsClient(get(named("NoAuthKtorClient"))) }
    single { UserClient(get(named("NoAuthKtorClient"))) }

    // --- OTHER ---

    single {
        val context = androidContext()
        context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }
}
