package com.solutionium.data.woo.categories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WooCategoryModule {
    @Binds
    abstract fun bindWooCategoryRepository(
        impl: WooCategoryRepositoryImpl
    ): WooCategoryRepository
}