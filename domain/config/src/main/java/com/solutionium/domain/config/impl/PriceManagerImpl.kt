package com.solutionium.domain.config.impl

import com.solutionium.data.model.ProductDetail
import com.solutionium.data.model.ProductThumbnail
import com.solutionium.domain.config.PriceManager
import javax.inject.Singleton

@Singleton
internal class PriceManagerImpl : PriceManager {
    override fun setFullPaymentAmount(percentage: Double) {
        TODO("Not yet implemented")
    }

    override fun calculateDisplayPrice(product: ProductThumbnail): Double {
        TODO("Not yet implemented")
    }

    override fun calculateDisplayPrice(product: ProductDetail): Double {
        TODO("Not yet implemented")
    }
}