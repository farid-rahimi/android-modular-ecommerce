package com.solutionium.data.api.woo.converters

import com.solutionium.data.model.AttributeTerm
import com.solutionium.data.model.Brand
import com.solutionium.data.model.ProductAttribute
import com.solutionium.data.model.ProductCatType
import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.data.model.ProductVarType
import com.solutionium.data.model.SimpleTerm
import com.solutionium.shared.data.network.common.valueString
import com.solutionium.shared.data.network.response.WooAttributeResponse
import com.solutionium.shared.data.network.response.WooBrandResponse
import com.solutionium.shared.data.network.response.WooCategory
import com.solutionium.shared.data.network.response.WooProduct
import com.solutionium.shared.data.network.response.WooProductDetailsResponse
import com.solutionium.shared.data.network.response.WooTerm


fun WooProduct.toProductThumbnail(): ProductThumbnail {

    var product = ProductThumbnail(
        id = id ?: 0,
        name = title ?: "",
        price = price?.toDouble() ?: 0.0,
        salePrice = if (salePrice.isNullOrBlank()) null else salePrice?.toDouble(),
        regularPrice = if (regularPrice.isNullOrBlank() || onSale != true) null else regularPrice?.toDouble(),
        onSale = onSale ?: false,
        imageUrl = thumbnail ?: "",
        shortDescription = "",
        brands = brands?.map { it.toSimpleTerm() } ?: emptyList(),
        tags = tags?.map { it.toSimpleTerm() } ?: emptyList(),
        categories = categories?.map { it.toSimpleTerm() } ?: emptyList(),
        decants = attributes?.find { it.slug == "pa_decant" }?.options,
        varType = if (productType == "variable") ProductVarType.VARIABLE else ProductVarType.SIMPLE,
        type = categories?.find { it.slug == "perfume" }?.let { ProductCatType.PERFUME }
            ?: categories?.find { it.slug == "shoes" }?.let { ProductCatType.SHOES }
            ?: ProductCatType.OTHER, // from categories
        scentGroup = attributes?.find { it.slug == "pa_scent-group" }?.options,
        volume = attributes?.find { it.slug == "pa_volume" }?.options?.firstOrNull(),
        sizingRange = attributes?.find { it.slug == "pa_size" }?.options?.let { "${it.first()}-${it.last()}" },
        rating = rating?.toDouble() ?: 0.0,
        ratingCount = ratingCount ?: 0,
        manageStock = manageStock ?: false,
        stock = stockQty ?: 0,
        stockStatus = stockStatus ?: "",
        hasSimpleAddToCart = productType == "simple",
        shippingClass = shippingClass?.slug,
        appOffer = appOffer ?.toDoubleOrNull() ?: 0.0,
    )

    if (product.appOffer > 0 && !product.onSale) {
        val discountedPrice = product.price * (1 - product.appOffer / 100)
        product = product.copy(
            regularPrice = product.price,
            price = discountedPrice,
            salePrice = discountedPrice,
            onSale = true
        )
    }

    return product
}

fun WooProductDetailsResponse.toProductThumbnail(): ProductThumbnail {


    var product = ProductThumbnail(
        id = id ?: 0, //
        name = name ?: "", //
        price = price?.toDouble() ?: 0.0, //
        salePrice = if (salePrice.isNullOrBlank()) null else salePrice?.toDouble(), //
        regularPrice = if (regularPrice.isNullOrBlank() || onSale != true) null else regularPrice?.toDouble(), //
        onSale = onSale ?: false, //
        imageUrl = images?.firstOrNull()?.thumbnail ?: "", //
        shortDescription = shortDescription ?: "", // No usage
        brands = brands?.map { it.toSimpleTerm() } ?: emptyList(), //
        tags = tags?.map { it.toSimpleTerm() } ?: emptyList(), //
        categories = categories?.map { it.toSimpleTerm() } ?: emptyList(), //
        decants = attributes?.find { it.slug == "pa_decant" }?.options,
        varType = if (type == "variable") ProductVarType.VARIABLE else ProductVarType.SIMPLE,
        type = categories?.find { it.slug == "perfume" }?.let { ProductCatType.PERFUME }
            ?: categories?.find { it.slug == "shoes" }?.let { ProductCatType.SHOES }
            ?: ProductCatType.OTHER, // from categories
        scentGroup = attributes?.find { it.slug == "pa_scent-group" }?.options,
        volume = attributes?.find { it.slug == "pa_volume" }?.options?.firstOrNull(),
        sizingRange = attributes?.find { it.slug == "pa_size" }?.options?.let { "${it.first()}-${it.last()}" },
        rating = averageRating?.toDouble() ?: 0.0, //
        ratingCount = ratingCount ?: 0,
        manageStock = manageStock ?: false,
        stock = stockQuantity ?: 0, //
        stockStatus = stockStatus ?: "", //
        hasSimpleAddToCart = type == "simple",
        shippingClass = shippingClass,
        appOffer = metaData?.find { it.key == "_app_offer" }?.valueString()?.toDoubleOrNull()
            ?: 0.0,
        //rotatingFeatures = listOf("Free Shipping" , "30-Day Returns", "Authentic Products").map { ProductFeatureText(it) }

    )
    if (product.appOffer > 0 && !product.onSale) {
        val discountedPrice = product.price * (1 - product.appOffer / 100)
        product = product.copy(
            regularPrice = product.price,
            price = discountedPrice,
            salePrice = discountedPrice,
            onSale = true
        )
    }

    return product
}

fun WooProductDetailsResponse.toProductDetail(): ProductDetail {
    var product = ProductDetail(
        id = id ?: 0,
        name = name ?: "",
        permalink = permalink ?: "",
        price = price?.toDouble() ?: 0.0,
        salePrice = if (salePrice.isNullOrBlank()) null else salePrice?.toDouble(),
        priceHtml = priceHTML ?: "",
        regularPrice = if (regularPrice.isNullOrBlank() || onSale != true) null else regularPrice?.toDouble(),
        onSale = onSale ?: false,
        imageUrls = images?.map { it.src ?: "" } ?: emptyList(),
        description = description ?: "",
        shortDescription = shortDescription ?: "",
        purchasable = purchasable ?: true,
        brands = brands?.map { it.toSimpleTerm() } ?: emptyList(),
        categories = categories?.map { it.toSimpleTerm() } ?: emptyList(),
        tags = tags?.map { it.toSimpleTerm() } ?: emptyList(),
        decantsStr = attributes?.find { it.slug == "pa_decant" }?.options,
        varType = if (type == "variable") ProductVarType.VARIABLE else ProductVarType.SIMPLE,
        type = categories?.find { it.slug == "perfume" }?.let { ProductCatType.PERFUME }
            ?: categories?.find { it.slug == "shoes" }?.let { ProductCatType.SHOES }
            ?: ProductCatType.OTHER,
        scentGroup = attributes?.find { it.slug == "pa_scent-group" }?.options,
        volume = attributes?.find { it.slug == "pa_volume" }?.options?.firstOrNull(),
        sizingRange = attributes?.find { it.slug == "pa_size" }?.options?.let { "${it.first()}-${it.last()}" },
        concentration = attributes?.find { it.slug == "pa_concentration" }?.options?.firstOrNull(),
        genderAffinity = attributes?.find { it.slug == "pa_gender" }?.options?.joinToString(" | "),
        rating = averageRating?.toDouble() ?: 0.0,
        ratingCount = ratingCount ?: 0,
        manageStock = manageStock ?: false,
        stock = stockQuantity ?: 0,
        stockStatus = stockStatus ?: "",
        attributes = attributes?.map {
            ProductAttribute(
                id = it.id ?: 0,
                name = it.name ?: "",
                slug = it.slug ?: "",
                visible = it.visible ?: false,
                position = it.position ?: 0,
                variation = it.variation ?: false,
                options = it.options ?: emptyList()
            )
        } ?: emptyList(),
        defaultAttributes = defaultAttributes?.map { it.toModel() } ?: emptyList(),
        variations = variations ?: emptyList(),
        shippingClass = shippingClass,
        appOffer = metaData?.find { it.key == "_app_offer" }?.valueString()?.toDoubleOrNull()
            ?: 0.0,

    )

    if (product.appOffer > 0 && !product.onSale) {
        val discountedPrice = product.price * (1 - product.appOffer / 100)
        product = product.copy(
            regularPrice = product.price,
            price = discountedPrice,
            salePrice = discountedPrice,
            onSale = true
        )
    }

    return product
}

fun WooAttributeResponse.toAttributeTerm(): AttributeTerm =
    AttributeTerm(
        id = id,
        name = name,
        slug = slug,
        description = description,
        menuOrder = menuOrder,
        count = count,
    )

fun WooBrandResponse.toBrand(): Brand =
    Brand(
        id = id,
        name = name,
        slug = slug,
        parent = parent,
        description = description,
        display = display,
        imageUrl = image?.thumbnail ?: image?.src ?: "",
        menuOrder = menuOrder ?: 0,
        count = count ?: 0
    )

fun WooCategory.toSimpleTerm() =
    SimpleTerm(
        id = id?.toInt() ?: 0,
        name = name ?: "",
        slug = slug ?: ""
    )

fun WooTerm.toSimpleTerm() =
    SimpleTerm(
        id = id ?: 0,
        name = name ?: "",
        slug = slug ?: ""
    )