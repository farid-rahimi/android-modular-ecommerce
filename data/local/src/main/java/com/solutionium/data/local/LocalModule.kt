package com.solutionium.data.local

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object LocalModule {

    @Singleton
    @Provides
    fun providesTokenStore(@ApplicationContext context: Context): TokenStore {
        return AuthTokenLocalDataSource(context)
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): AppPreferences {
        return AppPreferencesImpl(context) // Return the extension property instance
    }

}