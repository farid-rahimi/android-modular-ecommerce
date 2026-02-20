package com.solutionium.shared.data.network.request

import kotlinx.serialization.Serializable

@Serializable
data class DigitsLoginRequest(
    val user: String,
    val password: String
)
