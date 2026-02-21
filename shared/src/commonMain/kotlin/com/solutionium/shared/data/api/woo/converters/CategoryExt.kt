package com.solutionium.shared.data.api.woo.converters

import com.solutionium.shared.data.model.Category
import com.solutionium.shared.data.network.response.WooCategoryResponse

fun WooCategoryResponse.toCategory(): Category =
    Category(
        id = id ?: 0,
        name = name ?: "",
        parent = parent ?: 0,
        slug = slug ?: "",
        description = description ?: "",
        display = display ?: "",
        imageUrl = image?.src ?: "",
        menuOrder = menuOrder ?: 0,
        count = count ?: 0
    )
