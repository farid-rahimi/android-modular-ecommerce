package com.solutionium.data.woo.user

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WooUserModule {
    @Binds
    abstract fun bindWooUserRepository(
        impl: WooUserRepositoryImpl
    ): WooUserRepository

    @Binds
    abstract fun bindStoryViewRepository(
        impl: StoryViewRepositoryImpl
    ): StoryViewRepository
}