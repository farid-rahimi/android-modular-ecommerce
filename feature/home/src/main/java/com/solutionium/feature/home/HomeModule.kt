package com.solutionium.feature.home

import com.solutionium.shared.domain.cart.getCartDomainModules
import com.solutionium.shared.domain.config.getConfigDomainModules
import com.solutionium.domain.favorite.getFavoriteDomainModules
import com.solutionium.domain.user.getUserDomainModules
import com.solutionium.domain.woo.categories.getCategoryDomainModules
import com.solutionium.domain.woo.products.getProductsDomainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getHomeModules() = setOf(homeModule) + getCartDomainModules() + getFavoriteDomainModules() + getUserDomainModules() + getConfigDomainModules() + getProductsDomainModules() + getCategoryDomainModules()


val homeModule = module {
    viewModel {
        HomeViewModel(
            context = androidContext(),
            getProductsUseCase = get(),
            getCategoriesUseCase = get(),
            observeCartUseCase = get(),
            addToCart = get(),
            updateCartItem = get(),
            observeFavoritesUseCase = get(),
            toggleFavoriteUseCase = get(),
            homeBannerUseCase = get(),
            paymentMethodDiscountUseCase = get(),
            getStoriesUseCase = get(),
            addStoryViewUseCase = get(),
            getAllViewedStories = get(),
            getHeaderLogoUseCase = get(),
            checkLoginUserUseCase = get(),
            checkSuperUserUseCase = get(),
            getVersionsUseCase = get(),
            getContactInfoUseCase = get()
        )
    }
}
