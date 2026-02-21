package com.solutionium.data.cart

import com.solutionium.data.database.entity.CartItemEntity
import com.solutionium.data.database.entity.VariationAttributeSerializable
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.VariationAttribute

fun CartItemEntity?.toModel(): CartItem? {
    if (this == null) {
        return null // Or throw a custom exception, or return a default CartItem
    }


    return CartItem(
        productId = productId,
        variationId = variationId ,
        name = name,
        categoryIDs = categoryIDs,
        brandIDs = brandIDs,
        isDecant = isDecant,
        decVol = decVol,
        variationAttributes = variationAttributes.map { it.toModel() },
        currentPrice = currentPrice,
        regularPrice = regularPrice,
        currentStock = currentStock,
        manageStock = manageStock,
        stockStatus = stockStatus,
        quantity = quantity,
        imageUrl = imageUrl,

        requiresAttention = requiresAttention,
        validationInfo = validationInfo,
        shippingClass = shippingClass,
        appOffer = appOffer
    )
}

fun CartItem.toEntity() = CartItemEntity(
    productId = productId,
    variationId = variationId,
    name = name,
    categoryIDs = categoryIDs,
    brandIDs = brandIDs,
    isDecant = isDecant,
    decVol = decVol,
    variationAttributes = variationAttributes.map { it.toSerializable() },
    currentPrice = currentPrice,
    regularPrice = regularPrice,
    currentStock = currentStock,
    manageStock = manageStock,
    stockStatus = stockStatus,
    quantity = quantity,
    imageUrl = imageUrl,
    requiresAttention = requiresAttention,
    validationInfo = validationInfo,
    shippingClass = shippingClass,
    appOffer = appOffer
)

fun VariationAttribute.toSerializable() = VariationAttributeSerializable(
    id = id,
    name = name,
    slug = slug,
    option = option
)

fun VariationAttributeSerializable.toModel() = VariationAttribute(
    id = id,
    name = name,
    slug = slug,
    option = option
)