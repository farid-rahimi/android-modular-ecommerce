package com.solutionium.domain.woo.products

import com.solutionium.shared.data.products.productsDataModule
import com.solutionium.domain.woo.products.impl.GetAttributeTermsUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetBrandsUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetProductDetailsUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetProductListStreamUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetProductVariationsUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetProductsListUseCaseImpl
import com.solutionium.domain.woo.products.impl.SearchProductsUseCaseImpl
import org.koin.dsl.module

fun getProductsDomainModules() = setOf(productsDomainModule, productsDataModule)


val productsDomainModule = module {
    factory<GetProductDetailsUseCase> { GetProductDetailsUseCaseImpl(get()) }
    factory<GetProductsListUseCase> { GetProductsListUseCaseImpl(get()) }
    factory<GetProductListStreamUseCase> { GetProductListStreamUseCaseImpl(get()) }
    factory<GetBrandsUseCase> { GetBrandsUseCaseImpl(get()) }
    factory<GetAttributeTermsUseCase> { GetAttributeTermsUseCaseImpl(get()) }
    factory<GetProductVariationsUseCase> { GetProductVariationsUseCaseImpl(get()) }
    factory<SearchProductsUseCase> { SearchProductsUseCaseImpl(get()) }
}
