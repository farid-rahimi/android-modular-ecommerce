package com.solutionium.domain.woo.categories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WooCategoryUseCaseModule {
    @Binds
    abstract fun bindCategoryListUseCase(
        impl: GetCategoryListUseCaseImpl
    ): GetCategoryListUseCase

}