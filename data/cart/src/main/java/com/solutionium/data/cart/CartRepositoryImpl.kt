package com.solutionium.data.cart

import com.solutionium.data.database.dao.CartDao
import com.solutionium.shared.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override suspend fun addCartItem(cartItem: CartItem) =
        cartDao.insertCartItem(cartItem.toEntity())


    override suspend fun removeCartItem(cartItem: CartItem) =
        cartDao.deleteCartItem(cartItem.toEntity())


    override suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem.toEntity())
    }


    override suspend fun increaseCartItemQuantity(cartItemId: Int, variationId: Int) =
        cartDao.increaseCartItemQuantity(cartItemId, variationId)


    override suspend fun decreaseCartItemQuantity(cartItemId: Int, variationId: Int) {
        val item = cartDao.getCartItemById(cartItemId, variationId)
        if (item?.quantity == 1)
            cartDao.deleteCartItem(item)
         else
            cartDao.decreaseCartItemQuantity(cartItemId, variationId)
    }


    override suspend fun clearCart() =
        cartDao.deleteAllCartItems()

    override fun getCartItemByProduct(productId: Int, variationId: Int): Flow<CartItem?> {
        return cartDao.getItemByProductId(productId, variationId).map { it.toModel() }
    }

    override fun getCartItems(): Flow<List<CartItem>> {

        return cartDao.getAllCartItems().map { entities ->
            entities.map { it.toModel()!! }
        }
    }

    override suspend fun confirmValidation() {
        cartDao.confirmValidation()
    }

//    override fun getItemQuantityByProduct(id: Int): Flow<CartItem> {
//        return cartDao.getItemQuantity(id).map { it.toModel() }
//    }

}