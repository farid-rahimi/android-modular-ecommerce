package com.solutionium.shared.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual val platformEngine: HttpClientEngine
    get() = Darwin.create()