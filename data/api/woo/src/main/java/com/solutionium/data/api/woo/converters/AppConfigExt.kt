package com.solutionium.data.api.woo.converters

import com.solutionium.data.model.AppConfig
import com.solutionium.data.model.AppVersion
import com.solutionium.data.model.BACSDetails
import com.solutionium.data.model.BannerItem
import com.solutionium.data.model.ContactInfo
import com.solutionium.data.model.Link
import com.solutionium.data.model.LinkType
import com.solutionium.data.model.ReviewCriteria
import com.solutionium.data.model.StoryItem
import com.solutionium.data.network.response.AppConfigResponse
import com.solutionium.data.network.response.AppVersionResponse
import com.solutionium.data.network.response.BACSDetailsResponse
import com.solutionium.data.network.response.ConfigLink
import com.solutionium.data.network.response.ContactResponse
import com.solutionium.data.network.response.HomeBanner
import com.solutionium.data.network.response.StoryItemR

fun AppConfigResponse.toModel() = AppConfig(
    message = message,

    headerLogoUrl = headerLogo,

    stories = stories?.map { it.toModel() } ?: emptyList(),

    homeBanners = homeBanners?.map { it.toModel() } ?: emptyList(),

    paymentDiscount = paymentDiscount?.associate { it.methodID.orEmpty() to (it.amount ?: 0.0) }
        ?: emptyMap(),
    paymentForceEnabled = paymentForceEnabled ?: emptyList(),
    bacsDetails = bacsDetailsResponse?.toModel(),
    images = images?.associate { it.termID to (it.src.orEmpty()) } ?: emptyMap(),
    freeShippingMethodID = freeShippingMethodID,
    reviewCriteria = reviewCriteria?.map {
        ReviewCriteria(
            catID = it.catID,
            criteria = it.criteria
        )
    } ?: emptyList(),
    appVersion = appVersion?.toModel(),
    contact = contact?.toModel()
)

fun HomeBanner.toModel() = BannerItem(
    id = id ?: 0,
    title = title.orEmpty(),
    subTitle = subTitle,
    link = link?.toModel(),
    imageUrl = src.orEmpty()
)

fun ConfigLink.toModel() = Link(
    title = title,
    type = LinkType.fromValue(type.orEmpty()) ?: LinkType.EXTERNAL,
    target = target.orEmpty()
)

fun StoryItemR.toModel() = StoryItem(
    id = id,
    title = title.orEmpty(),
    subtitle = subtitle,
    mediaUrl = mediaUrl.orEmpty(),
    link = link?.toModel()
)

fun BACSDetailsResponse.toModel() = BACSDetails(
    cardNumber = cardNumber,
    ibanNumber = ibanNumber,
    accountHolder = accountHolder,
    contactNumber = contactNumber
)

fun AppVersionResponse.toModel() = AppVersion(
    latestVersion = latestVersion,
    minRequiredVersion = minRequiredVersion
)

fun ContactResponse.toModel() = ContactInfo(
    call = call.orEmpty(),
    whatsapp = whatsapp.orEmpty(),
    instagram = instagram.orEmpty(),
    telegram = telegram.orEmpty(),
    email = email.orEmpty()
)

