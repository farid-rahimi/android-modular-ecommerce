package com.solutionium.feature.product.detail

import com.solutionium.shared.domain.cart.getCartDomainModules
import com.solutionium.shared.domain.config.getConfigDomainModules
import com.solutionium.shared.domain.favorite.getFavoriteDomainModules
import com.solutionium.domain.review.getReviewDomainModules
import com.solutionium.domain.woo.products.getProductsDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getProductDetailModules() = setOf(productDetailModule) + getCartDomainModules() + getProductsDomainModules() + getFavoriteDomainModules() + getReviewDomainModules() + getConfigDomainModules()


val productDetailModule = module {
    viewModel {
        ProductDetailViewModel(
            savedStateHandle = get(),
            getProductDetails = get(),
            getProductVariations = get(),
            addToCart = get(),
            updateCartItemUseCase = get(),
            getProductInCartQuantity = get(),
            observeFavoritesUseCase = get(),
            toggleFavoriteUseCase = get(),
            paymentMethodDiscountUseCase = get(),
            getTopReviews = get()
        )
    }
}
