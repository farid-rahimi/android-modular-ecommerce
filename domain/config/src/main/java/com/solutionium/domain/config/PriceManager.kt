package com.solutionium.domain.config

import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.ProductThumbnail

interface PriceManager {

    fun setFullPaymentAmount(percentage: Double)

    fun calculateDisplayPrice(product: ProductThumbnail): Double

    fun calculateDisplayPrice(product: ProductDetail): Double

}