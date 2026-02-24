package com.solutionium.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.ChangeType
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.UiText
import com.solutionium.shared.data.model.ValidationInfo
import com.solutionium.shared.domain.cart.ClearCartUseCase
import com.solutionium.shared.domain.cart.ConfirmValidationUseCase
import com.solutionium.shared.domain.cart.ObserveCartUseCase
import com.solutionium.shared.domain.cart.UpdateCartItemUseCase
import com.solutionium.shared.domain.cart.ValidateCartUseCase
import com.solutionium.shared.domain.config.PaymentMethodDiscountUseCase
import com.solutionium.domain.user.CheckLoginUserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val validateCartUseCase: ValidateCartUseCase,
    private val confirmValidation: ConfirmValidationUseCase,
    private val paymentMethodDiscountUseCase: PaymentMethodDiscountUseCase,
    private val checkLoginUserUseCase: CheckLoginUserUseCase,


    ) : ViewModel() {

    private val _uiState = MutableStateFlow(CartScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        observeCart()
        validateCart()
        loadPaymentMethodDiscounts()
        //checkLoginStatus()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            // Re-run all the initial data fetching logic
            validateCart()
            // A small delay can make the UI feel smoother
            delay(600)
            _isRefreshing.value = false
        }
    }


    // This function will be called when the user clicks the "Checkout" button
    fun onCheckoutClick(
        onNavigateToCheckout: () -> Unit
    ) {
        viewModelScope.launch {

            val isLoggedIn = checkLoginUserUseCase().first()
            if (isLoggedIn) {
                onNavigateToCheckout()
            } else {
                _uiState.update { it.copy(showLoginPrompt = true) }
            }
        }
    }

    // Call this to dismiss the dialog
    fun dismissLoginPrompt() {
        _uiState.update { it.copy(showLoginPrompt = false) }
    }


    private fun loadPaymentMethodDiscounts() {
        viewModelScope.launch {
            try {
                val discounts = paymentMethodDiscountUseCase()
                _uiState.update { it.copy(paymentDiscount = discounts.values.max()) }
            } catch (e: Exception) {
                // Handle error if needed
                _uiState.update { it.copy(paymentDiscount = 0.0) }
            }

        }
    }

    private fun observeCart() {

        viewModelScope.launch {
            observeCartUseCase().collect { items ->
                val totalPrice = items.sumOf { it.currentPrice * it.quantity }
                val itemsNeedAttention = items.any { it.requiresAttention }
                val cartItemCount = items.sumOf { it.quantity }

                _uiState.update { stste ->
                    stste.copy(
                    //isLoading = false,
                    cartItems = items,
                    totalPrice = totalPrice,
                    cartItemCount = cartItemCount,
                    hasAttentionItems = itemsNeedAttention,
                    needsRevalidation = if(itemsNeedAttention && !stste.isLoading) true else stste.needsRevalidation,
                    lastValidationError = if (itemsNeedAttention) items.firstOrNull { it.validationInfo != null }?.validationInfo else null

                )}
                //_uiState.value = _uiState.value?.copy(cartItems = it)
                //_cartItems.value = it
            }
        }

    }

    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch {
            updateCartItemUseCase.removeCartItem(cartItem)
        }
    }

    fun increaseQuantity(cartItem: CartItem) {

        viewModelScope.launch {
            if (cartItem.quantity >= (cartItem.currentStock ?: 12) || cartItem.quantity >= 12) return@launch // Max limit reached
            updateCartItemUseCase.increaseCartItemQuantity(cartItem.productId, cartItem.variationId)
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {

        viewModelScope.launch {
            updateCartItemUseCase.decreaseCartItemQuantity(cartItem.productId, cartItem.variationId)
        }
    }

    fun validateCart() {
        viewModelScope.launch {
            _uiState.update { it.copy(isPerformingValidation = true, validationError = null, validationSummaryId = null) }
            validateCartUseCase()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val validationResults = result.data
                            val issueCount = validationResults.count { it.cartItem.requiresAttention }
                            val summary = if (issueCount > 0) {
                                R.string.cart_updated_items_title
                            } else {
                                R.string.all_items_updated_msg
                            }
                            _uiState.update { it.copy(isPerformingValidation = false, validationSummaryId = summary) }
                        }
                        is Result.Failure -> {
                            val errorMessage = when(val error = result.error) {
                                is GeneralError.NetworkError -> "Network Error: Please check your connection."
                                is GeneralError.UnknownError -> "An unknown error occurred during validation."
                                else -> "Failed to validate cart."
                            }
                            _uiState.update { it.copy(isPerformingValidation = false, validationError = errorMessage) }
                        }
                    }
                }
                //.launchIn(viewModelScope)
        }
    }

    fun confirmCartValidation() {
        viewModelScope.launch {
            //_uiState.update { it.copy(isLoading = true, validationResults = null, lastValidationError = null) }
            confirmValidation()
        }
    }

    fun mapValidationInfoToUiText(validationInfo: ValidationInfo?): UiText? {
        if (validationInfo == null) return null

        return when (validationInfo.type) {
            ChangeType.PRICE_CHANGED -> UiText.StringResource(
                R.string.cart_validation_price_changed,
                validationInfo.values
            )
            ChangeType.REGULAR_PRICE_CHANGED -> UiText.StringResource(
                R.string.cart_validation_regular_price_changed,
                validationInfo.values
            )
            ChangeType.STOCK_CHANGED -> UiText.StringResource(
                R.string.cart_validation_stock_changed,
                validationInfo.values
            )
            ChangeType.OUT_OF_STOCK -> UiText.StringResource(R.string.cart_validation_out_of_stock_short)
            ChangeType.NOT_AVAILABLE -> UiText.StringResource(R.string.cart_validation_out_of_stock_short)
            ChangeType.MULTIPLE_ISSUES -> UiText.StringResource(R.string.cart_validation_multiple_issues)
            ChangeType.NETWORK_ERROR -> UiText.StringResource(R.string.cart_validation_network_error)
        }
    }


}
