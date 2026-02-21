package com.solutionium.data.database.dao

import android.icu.math.BigDecimal
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.solutionium.data.database.entity.CartItemEntity
import com.solutionium.shared.data.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    @Query("SELECT * FROM cart_item WHERE productId = :productId AND variationId = :variationId LIMIT 1")
    suspend fun getCartItemById(productId: Int, variationId: Int): CartItemEntity?

    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_item")
    suspend fun deleteAllCartItems()

    @Query("SELECT SUM(quantity) FROM cart_item")
    fun getTotalItemCount(): Flow<Int?>

    @Query("UPDATE cart_item SET quantity = quantity + 1 WHERE productId = :productId AND variationId = :variationId")
    suspend fun increaseCartItemQuantity(productId: Int, variationId: Int)

    @Query("UPDATE cart_item SET quantity = quantity - 1 WHERE productId = :productId AND variationId = :variationId")
    suspend fun decreaseCartItemQuantity(productId: Int, variationId: Int)

    // You might add more specific update methods if needed
//    @Query("UPDATE cart_item SET quantity = :newQuantity, currentPrice = :newPrice, requiresAttention = :requiresAttention, validationMessage = :validationMessage WHERE productId = :productId")
//    suspend fun updateValidatedItem(
//        productId: String,
//        newQuantity: Int,
//        newPrice: Double,
//        requiresAttention: Boolean,
//        validationMessage: String?
//    )

    @Query("SELECT * FROM cart_item")
    fun getAllCartItems(): Flow<List<CartItemEntity>>



    @Query("SELECT * FROM cart_item WHERE productId = :productId AND variationId = :variationId LIMIT 1")
    fun getItemByProductId(productId: Int, variationId: Int): Flow<CartItemEntity?>

    @Query("UPDATE cart_item SET requiresAttention = 0, validationInfo = NULL")
    suspend fun confirmValidation()

}
