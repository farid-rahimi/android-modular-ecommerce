package com.solutionium.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class PrivacyPolicyResponse (
    val status: String? = null,
    val message: String? = null,

)
