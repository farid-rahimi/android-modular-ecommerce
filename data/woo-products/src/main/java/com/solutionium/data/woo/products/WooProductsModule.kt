package com.solutionium.data.woo.products

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WooProductsModule {
    @Binds
    abstract fun bindWooProductsRepository(
        impl: WooProductRepositoryImpl
    ): WooProductRepository
}