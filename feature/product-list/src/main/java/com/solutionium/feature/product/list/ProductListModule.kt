package com.solutionium.feature.product.list

import com.solutionium.shared.domain.cart.getCartDomainModules
import com.solutionium.shared.domain.config.getConfigDomainModules
import com.solutionium.domain.favorite.getFavoriteDomainModules
import com.solutionium.domain.user.getUserDomainModules
import com.solutionium.domain.woo.products.getProductsDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getProductListModules() = setOf(productListModule) + getProductsDomainModules() + getCartDomainModules() + getFavoriteDomainModules() + getConfigDomainModules() + getUserDomainModules()

val productListModule = module {
    viewModel {
        ProductListViewModel(
            savedStateHandle = get(),
            productList = get(),
            observeCartUseCase = get(),
            addToCart = get(),
            updateCartItem = get(),
            observeFavoritesUseCase = get(),
            toggleFavoriteUseCase = get(),
            paymentMethodDiscountUseCase = get(),
            checkSuperUserUserCase = get()
        )
    }
}
