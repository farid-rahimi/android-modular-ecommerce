package com.solutionium.domain.cart.impl

import com.solutionium.data.cart.CartRepository
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.CartItemServer
import com.solutionium.shared.data.model.ChangeType
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.ValidationInfo
import com.solutionium.shared.data.products.WooProductRepository
import com.solutionium.domain.cart.CartItemValidationResult
import com.solutionium.domain.cart.CartItemValidationStatus
import com.solutionium.domain.cart.ValidateCartUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.text.NumberFormat
import java.util.Locale

class ValidateCartUseCaseImpl(
    private val cartRepository: CartRepository,
    private val productRepository: WooProductRepository,
): ValidateCartUseCase {
    override suspend fun invoke(): Flow<Result<List<CartItemValidationResult>, GeneralError>> = flow{
        emit(validateCart())
    }

    private suspend fun validateCart(): Result<List<CartItemValidationResult>, GeneralError> {

        val localCartItems = cartRepository.getCartItems().first() // Get current snapshot
        if (localCartItems.isEmpty()) {
            return Result.Success(emptyList())
        }

        val productIds = localCartItems.map { if (!it.isDecant && it.variationId != 0) it.variationId else it.productId }
        when (val serverDetailsResource = productRepository.getCartUpdateServer(productIds)) {
            is Result.Success -> {
                val serverProductsMap = serverDetailsResource.data.associateBy { it.id }
                val validationResults = mutableListOf<CartItemValidationResult>()
                var cartModified = false

                for (localItem in localCartItems) {
                    val serverProduct = serverProductsMap[if (!localItem.isDecant && localItem.variationId != 0) localItem.variationId else localItem.productId]
                    val result = validateSingleItem(localItem, serverProduct)
                    validationResults.add(result)

                    // Update local cart item based on validation
                    val updatedItem = applyValidationToCartItem(localItem, result, serverProduct)
                    if (updatedItem.productId == -1) { // Special marker to delete item
                        cartRepository.removeCartItem(localItem)
                        cartModified = true
                    } else if (updatedItem != localItem) {
                        cartRepository.updateCartItem(updatedItem)
                        cartModified = true
                    }
                }
                // Optionally: emit a special event or use a different return type if cartModified
                return Result.Success(validationResults)
            }
            is Result.Failure -> {
                // Mark all items as requiring attention due to network error or return error
                localCartItems.forEach {
                    cartRepository.updateCartItem(it.copy(requiresAttention = true, validationInfo = ValidationInfo(ChangeType.NETWORK_ERROR)))
                }
                return Result.Failure( GeneralError.UnknownError(Throwable("Failed to validate cart with server.")))
            }
        }
    }

    private fun validateSingleItem(localItem: CartItem, serverProduct: CartItemServer?): CartItemValidationResult {
        if (serverProduct == null ) {//|| !serverProduct.purchasable) {
            return CartItemValidationResult(localItem, CartItemValidationStatus.NotAvailable)
        }

        //val serverPrice = serverProduct.regularPrice ?: serverProduct.price
        val issues = mutableListOf<CartItemValidationStatus>()

        val serverPrice = serverProduct.price

        // 1. Price & Regular Check
        if (localItem.appOffer == 0.0 && !localItem.isDecant && localItem.currentPrice.compareTo(serverProduct.price) != 0) {
            issues.add(CartItemValidationStatus.PriceChanged(localItem.currentPrice, serverProduct.price))
        }
        if (localItem.appOffer == 0.0 && !localItem.isDecant && localItem.regularPrice != null && localItem.regularPrice?.compareTo(serverProduct.regularPrice) != 0) {
            issues.add(CartItemValidationStatus.RegularPriceChanged(localItem.regularPrice ?: 0.0, serverProduct.regularPrice))
        }


        // 2. Stock Check
        val serverStock = serverProduct.stockQuantity
        if (serverProduct.manageStock) { // If stock is managed
            if (serverStock <= 0 && !localItem.isDecant) {
                issues.add(CartItemValidationStatus.OutOfStock)
            } else if (localItem.quantity > serverStock && !localItem.isDecant) {
                issues.add(CartItemValidationStatus.StockNotEnough(localItem.currentStock, serverStock, localItem.quantity))
            } else if ((localItem.currentStock ?: 0) < serverStock) {
                issues.add(CartItemValidationStatus.StockIncreased(serverStock))

            }
            // Optional: If localItem.currentStock was significantly different from serverStock, note it
            if (localItem.isDecant && serverStock == 0 && serverProduct.backOrder == "no") {
                issues.add(CartItemValidationStatus.OutOfStock)
                // Could be a less critical StockChanged if quantity is still fine
                // issues.add(CartItemValidationStatus.StockChanged(localItem.currentStock, serverStock, localItem.quantity))
            }
        } else {
            if (serverProduct.stockStatus != "instock" && !localItem.isDecant) {
                issues.add(CartItemValidationStatus.OutOfStock)
            }
            if (localItem.isDecant && serverProduct.stockStatus == "outofstock") {
                issues.add(CartItemValidationStatus.OutOfStock)
            }
            // If stock not managed, we might still want to update currentStock to null or a sentinel value
            // localItem = localItem.copy(currentStock = null)
        }

        return when {
            issues.isEmpty() -> CartItemValidationResult(localItem, CartItemValidationStatus.Valid, serverPrice, serverStock)
            issues.size == 1 -> CartItemValidationResult(localItem, issues.first(), serverPrice, serverStock)
            else -> CartItemValidationResult(localItem, CartItemValidationStatus.MultipleIssues(issues), serverPrice, serverStock)
        }
    }


    private fun applyValidationToCartItem(
        originalItem: CartItem,
        validationResult: CartItemValidationResult,
        serverProduct: CartItemServer? // Can be null if item not available
    ): CartItem {
        var updatedItem = originalItem.copy(
            requiresAttention = false, // Reset first
            validationInfo = null
        )
        val serverPrice = validationResult.serverPrice ?: originalItem.currentPrice
        val serverStock = validationResult.serverStock
        val serverManagesStock = serverProduct?.manageStock ?: originalItem.manageStock
//        val serverStockStatus = serverProduct?.stockStatus
//        val serverBackOrder = serverProduct?.backOrder

        when (val status = validationResult.status) {
            is CartItemValidationStatus.Valid -> {
//                updatedItem = updatedItem.copy(
//                    currentPrice = serverPrice,
//                    currentStock = if (serverManagesStock) serverStock else null,
//                    manageStock = serverManagesStock,
//                )
            }
            is CartItemValidationStatus.PriceChanged -> {
                updatedItem = updatedItem.copy(
                    currentPrice = status.newPrice,
                    //currentStock = if (serverManagesStock) serverStock else null,
                    //manageStock = serverManagesStock,
                    requiresAttention = true,
                    validationInfo = ValidationInfo(
                        type = ChangeType.PRICE_CHANGED,
                        values = listOf(status.newPrice.toFormattedString(), status.oldPrice.toFormattedString())
                    )
                    //validationMessage = "Price changed to ${status.newPrice} from ${status.oldPrice}"
                )
            }

            is CartItemValidationStatus.RegularPriceChanged -> {
                updatedItem = updatedItem.copy(
                    regularPrice = status.newPrice,
                    //currentPrice = serverPrice,
                    //currentStock = if (serverManagesStock) serverStock else null,
                    //manageStock = serverManagesStock,
                    requiresAttention = true,
                    validationInfo = ValidationInfo(
                        type = ChangeType.REGULAR_PRICE_CHANGED,
                        values = listOf(status.newPrice.toFormattedString(), status.oldPrice.toFormattedString())
                    )
                    //validationMessage = "Regular price changed to ${status.newPrice} from ${status.oldPrice}"
                )
            }

            is CartItemValidationStatus.StockNotEnough -> {
                updatedItem = updatedItem.copy(
                    //currentPrice = serverPrice, // Update price even if stock changed
                    currentStock = if (serverManagesStock) status.newStock else null,
                    manageStock = serverManagesStock,

                    quantity = minOf(originalItem.quantity, status.newStock ?: originalItem.quantity), // Adjust quantity if needed
                    requiresAttention = true,
                    validationInfo = ValidationInfo(
                        type = ChangeType.STOCK_CHANGED,
                        values = listOf(originalItem.name, status.newStock.toString())
                    )
                    //validationMessage = "Stock for ${originalItem.name} is now ${status.newStock}. Quantity adjusted."
                )
            }
            is CartItemValidationStatus.StockIncreased -> {
                updatedItem = updatedItem.copy(
                    currentStock = if (serverManagesStock) status.newStock else null,
                )
            }
            is CartItemValidationStatus.OutOfStock -> {
//                updatedItem = updatedItem.copy(
//                    currentPrice = serverPrice,
//                    currentStock = 0,
//                    requiresAttention = true,
//                    validationMessage = "${originalItem.name} is out of stock."
//                    // Consider setting quantity to 0 or marking for deletion
//                )
                // Optionally, mark for deletion by returning a specific state or modifying the item
                return originalItem.copy(productId = -1) // Hacky, better to handle deletion separately
            }

            is CartItemValidationStatus.NotAvailable -> {
//                updatedItem = updatedItem.copy(
//                    requiresAttention = true,
//                    validationMessage = "${originalItem.name} is no longer available."
//                    // Consider marking for deletion
//                )
                return originalItem.copy(productId = -1)
            }
            is CartItemValidationStatus.MultipleIssues -> {
                status.issues.forEach { issue ->
                    updatedItem = applyValidationToCartItem(updatedItem.copy(), CartItemValidationResult(originalItem, issue, serverPrice, serverStock), serverProduct)
                }
//                val messages = status.issues.mapNotNull {
//                    when(it) {
//                        is CartItemValidationStatus.PriceChanged -> ValidationInfo.StringResource("cart_validation_price_short", listOf(it.newPrice.toString()))
//                        is CartItemValidationStatus.StockChanged -> ValidationInfo.StringResource("cart_validation_stock_short", listOf(it.newStock.toString()))
//                        is CartItemValidationStatus.OutOfStock -> ValidationInfo.StringResource("cart_validation_out_of_stock_short")
//                        else -> null
//                    }
//                }//.joinToString(" ")
//                updatedItem = updatedItem.copy(
//                    currentPrice = if (!originalItem.isDecant) serverPrice else originalItem.currentPrice,
//                    regularPrice =if (!originalItem.isDecant) serverProduct?.regularPrice else originalItem.currentPrice,
//                    manageStock = serverManagesStock,
//                    quantity = minOf(originalItem.quantity, serverProduct?.stockQuantity ?: originalItem.quantity),
//                    currentStock = if (serverProduct?.manageStock == true) serverStock else 0,
//                    requiresAttention = true,
//                    validationInfo = ValidationInfo(ChangeType.MULTIPLE_ISSUES)                    //validationMessage = messages.ifEmpty { "Multiple issues found." }
//                )
                if (status.issues.any { it is CartItemValidationStatus.OutOfStock || it is CartItemValidationStatus.NotAvailable }) {
                    // Mark for deletion or quantity adjustment
                }
            }


        }
        // Ensure quantity is not negative or zero if not explicitly handled for deletion
        //if (updatedItem.quantity <= 0 && status !is CartItemValidationStatus.OutOfStock && status !is CartItemValidationStatus.NotAvailable) {
            // This case should ideally be handled by quantity adjustment logic during StockChanged
        //}

        return updatedItem
    }
}


// Extension function to format a Double into a clean, comma-separated string.
fun Double.toFormattedString(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.US) // Or your desired Locale
    // Check if the number has a fractional part.
    return if (this % 1 == 0.0) {
        // It's a whole number, format without decimal places.
        numberFormat.format(this.toLong())
    } else {
        // It has decimal places, format as is.
        numberFormat.format(this)
    }
}




