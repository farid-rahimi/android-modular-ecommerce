package com.solutionium.data.api.woo.converters

import com.solutionium.data.model.Coupon
import com.solutionium.data.network.response.WooCouponResponse
import com.solutionium.data.network.response.excludedBrandIds
import com.solutionium.data.network.response.includedBrandIds

fun WooCouponResponse.toModel() = Coupon(
    id = id,
    code = code,
    amount = amount.toDouble(),
    discountType = discountType,
    description = description,
    dateExpires = dateExpires,
    usageCount = usageCount,
    usageLimit = usageLimit,
    usageLimitPerUser = usageLimitPerUser,
    individualUse = individualUse,

    productIDS = productIDS,
    excludedProductIDS = excludedProductIDS,

    productCategories = productCategories,
    excludedProductCategories = excludedProductCategories,

    brandIDS = includedBrandIds,
    excludedBrandIDS = excludedBrandIds,

    limitUsageToXItems = limitUsageToXItems,
    freeShipping = freeShipping,
    maximumAmount = maximumAmount.toDouble(),
    minimumAmount = minimumAmount.toDouble(),

    emailRestrictions = emailRestrictions ?: emptyList(),
    excludeSaleItems = excludeSaleItems

)

