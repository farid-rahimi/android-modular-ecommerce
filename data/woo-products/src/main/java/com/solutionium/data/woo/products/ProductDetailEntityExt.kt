package com.solutionium.data.woo.products

import com.solutionium.data.database.entity.ProductAttributeSerializable
import com.solutionium.data.database.entity.ProductDetailEntity
import com.solutionium.data.database.entity.VariationAttributeSerializable
import com.solutionium.shared.data.model.ProductAttribute
import com.solutionium.shared.data.model.ProductDetail
import com.solutionium.shared.data.model.ProductCatType
import com.solutionium.shared.data.model.VariationAttribute

fun ProductDetailEntity.toModel(): ProductDetail {

    return ProductDetail(
        id = id,
        name = name,
        permalink = "",
        price = price,
        imageUrls = imageUrls,
        rating = rating,
        ratingCount = ratingCount,
        shortDescription = shortDescription,
        description = description,
        categories = emptyList(),// category,
        brands = emptyList(), // brand,
        tags = emptyList(),
        varType = varType,
        type = ProductCatType.entries.find { it.value == type } ?: ProductCatType.OTHER,
        volume = attributes.find { it.slug == "pa_volume" }?.options?.first(),
        concentration = attributes.find { it.slug == "pa_concentration" }?.options?.first(),
        genderAffinity = attributes.find { it.slug == "pa_gender" }?.options?.joinToString(" | "),
        salePrice = salesPrice,
        regularPrice = regularPrice,
        onSale = onSale,
        manageStock = manageStock,
        priceHtml = priceHtml,
        stockStatus = stockStatus,
        stock = stock,
        attributes = attributes.map { it.toModel() },
        defaultAttributes = emptyList(),
        variations = variations,

    )
}

fun ProductDetail.toEntity(): ProductDetailEntity {
    return ProductDetailEntity(
        id = id,
        name = name,
        price = price,

        imageUrls = imageUrls,
        rating = rating,
        ratingCount = ratingCount,
        shortDescription = shortDescription,
        description = description,
        category = "",//category,
        varType = varType,
        type = type.value,
        brand = "",//brand,
        salesPrice = salePrice,
        regularPrice = regularPrice,
        onSale = onSale,
        priceHtml = priceHtml,
        stockStatus = stockStatus,
        stock = stock,
        manageStock = manageStock,
        attributes = attributes.map { it.toSerializable() },
        defaultAttributes = emptyList(), //defaultAttributes.map { it.toSerializable() }
        variations = variations,
    )


}

fun ProductAttribute.toSerializable() = ProductAttributeSerializable(
    id = id,
    name = name,
    slug = slug,
    variation = variation,
    options = options
)

fun ProductAttributeSerializable.toModel() = ProductAttribute(
    id = id,
    name = name,
    slug = slug,
    variation = variation,
    position = 0,
    options = options
)

