package com.solutionium.data.database

import android.content.Context
import androidx.room.Room
import com.solutionium.data.database.dao.AddressDao
import com.solutionium.data.database.dao.CartDao
import com.solutionium.data.database.dao.FavoriteDao
import com.solutionium.data.database.dao.ProductDetailDao
import com.solutionium.data.database.dao.StoryViewDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): WooDatabase {
        return Room.databaseBuilder(context, WooDatabase::class.java, "woo-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesProductDetailDao(db: WooDatabase): ProductDetailDao = db.productDetailDao()

    @Provides
    @Singleton
    fun providesCartDao(db: WooDatabase): CartDao = db.cartDao()

    @Provides
    @Singleton
    fun providesAddressDao(db: WooDatabase): AddressDao = db.addressDao()

    @Provides
    @Singleton
    fun providesFavoriteDao(db: WooDatabase): FavoriteDao = db.favoriteDao()

    @Provides
    @Singleton
    fun providesStoryViewDao(db: WooDatabase): StoryViewDao = db.storyViewDao()
}
