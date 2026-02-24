package com.solutionium.shared.domain.cart

import com.solutionium.shared.data.model.CartItem

sealed class CartItemValidationStatus {
    data object Valid : CartItemValidationStatus()
    data class PriceChanged(val oldPrice: Double, val newPrice: Double) : CartItemValidationStatus()
    data class RegularPriceChanged(val oldPrice: Double, val newPrice: Double) : CartItemValidationStatus()
    data class StockNotEnough(val oldStock: Int?, val newStock: Int?, val quantityInCart: Int) : CartItemValidationStatus() // newStock could be 0 for out of stock
    data class StockIncreased(val newStock: Int?) : CartItemValidationStatus() // newStock could be 0 for out of stock
    object OutOfStock : CartItemValidationStatus()
    object NotAvailable : CartItemValidationStatus() // Product removed or not purchasable
    data class MultipleIssues(val issues: List<CartItemValidationStatus>) : CartItemValidationStatus()
}

data class CartItemValidationResult(
    val cartItem: CartItem, // The original cart item
    val status: CartItemValidationStatus,
    val serverPrice: Double? = null, // Store the latest fetched price
    val serverStock: Int? = null        // Store the latest fetched stock
)