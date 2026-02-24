package com.solutionium.feature.category

import com.solutionium.shared.domain.config.getConfigDomainModules
import com.solutionium.shared.domain.user.getUserDomainModules
import com.solutionium.domain.woo.products.getProductsDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getCategoryModules() = setOf(categoryModule) + getProductsDomainModules() + getUserDomainModules() + getConfigDomainModules()

val categoryModule = module {
    viewModel {
        CategoryViewModel(
            getBrands = get(),
            getAttributeTerms = get(),
            getAppImages = get(),
            searchProducts = get(),
            checkSuperUserUserCase = get()
        )
    }
}
