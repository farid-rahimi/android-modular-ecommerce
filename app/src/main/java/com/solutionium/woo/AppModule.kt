package com.solutionium.woo


import com.solutionium.data.local.localModule
import com.solutionium.feature.account.getAccountModules
import com.solutionium.feature.address.getAddressModules
import com.solutionium.feature.cart.getCartFeatureModules
import com.solutionium.feature.category.getCategoryModules
import com.solutionium.feature.checkout.getCheckoutModules
import com.solutionium.feature.home.getHomeModules
import com.solutionium.feature.orders.getOrdersModules
import com.solutionium.feature.product.detail.getProductDetailModules
import com.solutionium.feature.product.list.getProductListModules
import com.solutionium.feature.review.getReviewModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { MainViewModel(get()) }
}

val allModules = (
    setOf(localModule, appModule) +
    getAccountModules() +
    getAddressModules() +
    getCartFeatureModules() +
    getCategoryModules() +
    getCheckoutModules() +
    getHomeModules() +
    getOrdersModules() +
    getProductDetailModules() +
    getProductListModules() +
    getReviewModules()
).toList()

