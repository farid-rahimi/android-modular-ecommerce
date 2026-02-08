package com.solutionium.data.api.woo.converters

import com.solutionium.data.model.MethodSetting
import com.solutionium.data.model.ShippingMethod
import com.solutionium.data.network.response.ShippingMethodResponse
import com.solutionium.data.network.response.ShippingMethodSetting

fun ShippingMethodResponse.toShippingMethod(): ShippingMethod =
    ShippingMethod(
        id = id ?: 0,
        title = title ?: "",
        methodId = methodID ?: "",
        description = methodDescription ?: "",
        cost = settings?.get("cost")?.value ?: "",
        settings = settings?.mapValues { it.value.toModel() }
    )

fun ShippingMethodSetting.toModel(): MethodSetting = MethodSetting(
    id, label, description, type, value, default, tip, placeholder
)