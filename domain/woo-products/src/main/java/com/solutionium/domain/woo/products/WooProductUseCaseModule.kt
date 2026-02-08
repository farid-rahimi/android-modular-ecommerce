package com.solutionium.domain.woo.products

import com.solutionium.domain.woo.products.impl.GetAttributeTermsUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetBrandsUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetProductDetailsUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetProductListStreamUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetProductVariationsUseCaseImpl
import com.solutionium.domain.woo.products.impl.GetProductsListUseCaseImpl
import com.solutionium.domain.woo.products.impl.SearchProductsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WooProductUseCaseModule {
    @Binds
    abstract fun bindProductDetailUseCase(
        impl: GetProductDetailsUseCaseImpl
    ): GetProductDetailsUseCase

    @Binds
    abstract fun bindNewProductsListUseCase(
        impl: GetProductsListUseCaseImpl
    ): GetProductsListUseCase

    @Binds
    abstract fun bindNewProductListStreamUseCase(
        impl: GetProductListStreamUseCaseImpl
    ): GetProductListStreamUseCase

    @Binds
    abstract fun bindGetBrandsUseCase(
        impl: GetBrandsUseCaseImpl
    ): GetBrandsUseCase

    @Binds
    abstract fun bindGetAttributeTermsUseCase(
        impl: GetAttributeTermsUseCaseImpl
    ): GetAttributeTermsUseCase

    @Binds
    abstract fun bindGetProductVariationsUseCase(
        impl: GetProductVariationsUseCaseImpl
    ): GetProductVariationsUseCase

    @Binds
    abstract fun bindSearchProductsUseCase(
        impl: SearchProductsUseCaseImpl
    ): SearchProductsUseCase
}