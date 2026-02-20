package com.solutionium.shared.data.network

import com.russhwolf.settings.Settings
import com.solutionium.shared.data.network.clients.DigitsClient
import com.solutionium.shared.data.network.clients.UserClient
import com.solutionium.shared.data.network.clients.WooCategoryClient
import com.solutionium.shared.data.network.clients.WooCheckoutOrderClient
import com.solutionium.shared.data.network.clients.WooOrderClient
import com.solutionium.shared.data.network.clients.WooProductClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import io.ktor.util.encodeBase64
import com.solutionium.shared.BuildKonfig

fun getNetworkDataModules() = setOf(networkModule)


expect val platformEngine: HttpClientEngine
val baseUrl: String = BuildKonfig.BASE_URL

val networkModule = module {

    single {
        Json { ignoreUnknownKeys = true }
    }

    // --- HTTP CLIENTS ---

    single(named("BasicAuthKtorClient")) {
        HttpClient(platformEngine) {
            install(ContentNegotiation) { json(get()) }
            //install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
            install(DefaultRequest) {
                url(baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                val auth = "${BuildKonfig.CONSUMER_KEY}:${BuildKonfig.CONSUMER_SECRET}"
                    .encodeToByteArray()
                    .encodeBase64() // This replaces Base64.encodeToString(it, Base64.NO_WRAP)

//                val auth = "${consumerKey}:${consumerSecret}"
//                    .encodeToByteArray().let { Base64.encodeToString(it, Base64.NO_WRAP) }
                header(HttpHeaders.Authorization, "Basic $auth")
            }
        }
    }

    single(named("BearerAuthKtorClient")) {
        HttpClient(platformEngine) {
            install(ContentNegotiation) { json(get()) }
            //install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
            install(DefaultRequest) {
                url(baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                // Inject TokenStore here if needed for dynamic Bearer tokens
            }
        }
    }

    single(named("NoAuthKtorClient")) {
        HttpClient(platformEngine) {
            install(ContentNegotiation) { json(get()) }
            //install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
            install(DefaultRequest) {
                url(baseUrl)
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

    single<Settings> { Settings() }
//    single {
//        val context = androidContext()
//        context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
//    }
}
