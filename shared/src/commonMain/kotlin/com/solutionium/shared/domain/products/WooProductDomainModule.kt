package com.solutionium.shared.domain.products

import com.solutionium.shared.data.products.productsDataModule
import com.solutionium.shared.domain.products.impl.GetAttributeTermsUseCaseImpl
import com.solutionium.shared.domain.products.impl.GetBrandsUseCaseImpl
import com.solutionium.shared.domain.products.impl.GetProductDetailsUseCaseImpl
import com.solutionium.shared.domain.products.impl.GetProductListStreamUseCaseImpl
import com.solutionium.shared.domain.products.impl.GetProductVariationsUseCaseImpl
import com.solutionium.shared.domain.products.impl.GetProductsListUseCaseImpl
import com.solutionium.shared.domain.products.impl.SearchProductsUseCaseImpl
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
