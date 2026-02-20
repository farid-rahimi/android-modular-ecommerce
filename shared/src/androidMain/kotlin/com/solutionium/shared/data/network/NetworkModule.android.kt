package com.solutionium.shared.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual val platformEngine: HttpClientEngine
    get() = OkHttp.create()