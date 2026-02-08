package com.solutionium.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solutionium.data.database.converter.ListConverters
import com.solutionium.data.database.converter.ProductAttributeConverter
import com.solutionium.data.database.converter.UiTextConverter
import com.solutionium.data.database.dao.AddressDao
import com.solutionium.data.database.dao.CartDao
import com.solutionium.data.database.dao.FavoriteDao
import com.solutionium.data.database.dao.ProductDetailDao
import com.solutionium.data.database.dao.StoryViewDao
import com.solutionium.data.database.entity.AddressEntity
import com.solutionium.data.database.entity.CartItemEntity
import com.solutionium.data.database.entity.FavoriteEntity
import com.solutionium.data.database.entity.ProductDetailEntity
import com.solutionium.data.database.entity.StoryViewEntity

@TypeConverters(ListConverters::class, ProductAttributeConverter::class, UiTextConverter::class)
@Database(
    entities = [
        ProductDetailEntity::class,
        CartItemEntity::class,
        AddressEntity::class,
        FavoriteEntity::class,
        StoryViewEntity::class
    ], version = 36, exportSchema = false
)
abstract class WooDatabase : RoomDatabase() {
    abstract fun productDetailDao(): ProductDetailDao
    abstract fun cartDao(): CartDao
    abstract fun addressDao(): AddressDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun storyViewDao(): StoryViewDao
}