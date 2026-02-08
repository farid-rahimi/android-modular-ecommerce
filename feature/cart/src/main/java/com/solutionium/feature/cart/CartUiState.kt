package com.solutionium.feature.cart

import com.solutionium.data.model.CartItem
import com.solutionium.data.model.UiText
import com.solutionium.data.model.ValidationInfo
import com.solutionium.domain.cart.CartItemValidationResult

data class CartScreenUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val cartItemCount: Int = 0,
    val isLoading: Boolean = false,
    val validationResults: List<CartItemValidationResult>? = null, // For displaying after validation
    val lastValidationError: ValidationInfo? = null,
    val needsRevalidation: Boolean = false, // Flag if background validation found changes
    val checkoutInProgress: Boolean = false,
    val isPerformingValidation: Boolean = false, // Specific state for the validation process
    val validationError: String? = null,
    val hasAttentionItems: Boolean = false,
    val validationSummaryId: Int? = null,
    val paymentDiscount: Double = 0.0,

    val isUserLoggedIn: Boolean = true, // Assume true by default
    val showLoginPrompt: Boolean = false // Controls the dialog


) {

    fun discountedPrice(originalPrice: Double?): Double? =
        originalPrice?.let { paymentDiscount.let { (100 - it) / 100 } * originalPrice }

}

