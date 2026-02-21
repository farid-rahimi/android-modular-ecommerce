package com.solutionium.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class AppConfig (

    val message: String? = null,

    val headerLogoUrl: String? = null,

    val stories: List<StoryItem> = emptyList(),

    val homeBanners: List<BannerItem> = emptyList(),

    val paymentDiscount: Map<String, Double> = emptyMap(),

    val paymentForceEnabled: List<String> = emptyList(),

    val bacsDetails: BACSDetails? = null,

    val images: Map<Int, String> = emptyMap(),

    val freeShippingMethodID: String? = null,

    val reviewCriteria: List<ReviewCriteria> = emptyList(),

    val appVersion: AppVersion? = null,

    val contact: ContactInfo? = null

)


data class BACSDetails (
    val cardNumber: String? = null,

    val ibanNumber: String? = null,

    val accountHolder: String? = null,

    val contactNumber: String? = null
)

data class ReviewCriteria (
    val catID: Int,

    val criteria: List<String>
)

data class ContactInfo (
    val call: String,
    val whatsapp: String,
    val instagram: String,
    val telegram: String,
    val email: String
)
