
package com.solutionium.shared.data.database

import androidx.room.Database
import androidx.room.RoomDatabaseConstructor
import androidx.room.ConstructedBy
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solutionium.shared.data.database.dao.StoryViewDao
import com.solutionium.shared.data.database.converter.ListConverters
import com.solutionium.shared.data.database.converter.ProductAttributeConverter
import com.solutionium.shared.data.database.converter.UiTextConverter
import com.solutionium.shared.data.database.dao.AddressDao
import com.solutionium.shared.data.database.dao.CartDao
import com.solutionium.shared.data.database.dao.FavoriteDao
import com.solutionium.shared.data.database.dao.ProductDetailDao
import com.solutionium.shared.data.database.entity.AddressEntity
import com.solutionium.shared.data.database.entity.CartItemEntity
import com.solutionium.shared.data.database.entity.FavoriteEntity
import com.solutionium.shared.data.database.entity.ProductDetailEntity
import com.solutionium.shared.data.database.entity.StoryViewEntity

@TypeConverters(ListConverters::class, ProductAttributeConverter::class, UiTextConverter::class)
@ConstructedBy(WooDatabaseConstructor::class)
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

//@Suppress("KotlinNoActualForExpect")
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object WooDatabaseConstructor : RoomDatabaseConstructor<WooDatabase> {
    override fun initialize(): WooDatabase
}
