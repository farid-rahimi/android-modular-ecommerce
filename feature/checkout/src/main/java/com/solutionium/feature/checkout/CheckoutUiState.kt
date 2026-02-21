package com.solutionium.feature.checkout

import androidx.annotation.StringRes
import com.solutionium.shared.data.model.Address
import com.solutionium.shared.data.model.BACSDetails
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.Coupon
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Order
import com.solutionium.shared.data.model.PaymentGateway
import com.solutionium.shared.data.model.ShippingMethod
import com.solutionium.shared.data.model.UserDetails
import com.solutionium.shared.data.model.UserWallet
import com.solutionium.domain.checkout.CouponError
import java.util.UUID

sealed class PlaceOrderStatus {
    data object Idle : PlaceOrderStatus() // Initial state
    data object InProgress : PlaceOrderStatus() // Loading
    data class AwaitingPayment(val paymentUrl: String, val orderId: Int) : PlaceOrderStatus()
    data class Success(val orderId: Int, val orderTotal: String) : PlaceOrderStatus() // Success
    data class BACSSuccess(
        val orderId: Int,
        val orderTotal: String,
        val bacsDetails: BACSDetails?
    ) : PlaceOrderStatus() // Success
    data class Failed(val errorMessage: String, val canRetry: Boolean = false) : PlaceOrderStatus() // Failure
}

data class CheckoutUiState(
    val isUserLoggedIn: Boolean? = null,
    val cartItems:List<CartItem> = emptyList(),
    val addressId: String? = null, // For editing/selection
    var shippingAddress: Address? = null, // Current selected/default address
    val isLoadingAddress: Boolean = false,
    val subTotal: Double = 0.0,
    val shippingCost: Double = 0.0,
    val totalFees: Double = 0.0,
    val totalDiscount: Double = 0.0,
    val total: Double = 0.0,
    val isInstallment: Boolean = false,
    val fees: MutableMap<String, Double> = emptyMap<String, Double>().toMutableMap(),
    val shippingMethods: List<ShippingMethod> = emptyList(),
    val freeShippingMethodByCoupon: ShippingMethod? = null,
    val freeShippingByCouponIsActive: Boolean = false,
    val freeShippingMethodByMinOrder: ShippingMethod? = null,
    val freeShippingByMinOrderIsActive: Boolean = false,
    val selectedShippingMethod: ShippingMethod? = null,
    val paymentGateways: List<PaymentGateway> = emptyList(),
    val selectedPaymentGateway: PaymentGateway? = null,
    val isLoadingShippingMethods: Boolean = false,
    val isLoadingPaymentGateways: Boolean = false,
    val message: String? = null,
    val error: CheckoutError? = null,
    val placeOrderStatus: PlaceOrderStatus = PlaceOrderStatus.Idle,
    val isApplyingCoupon: Boolean = false,
    val couponError: CouponError? = null,
    val appliedCoupons: List<Coupon> = emptyList(),
    val paymentMethodDiscounts: Map<String, Double> = emptyMap(),

    val availableAddresses: List<Address> = emptyList(), // <-- Add this
    val isAddressListExpanded: Boolean = false,         // <-- Add this to control the dropdown

    val userWallet: UserWallet? = null,
    val loadingWallet: Boolean = false,
    val useWallet: Boolean = false,
    val paidByWallet: Double = 0.0

)

/**
 * Represents specific, translatable errors that can occur on the checkout screen.
 * Each error state holds a string resource ID for its message.
 */
sealed class CheckoutError(@StringRes open val messageResId: Int) {

    data class EmptyCart(
        @StringRes override val messageResId: Int = R.string.empty_cart_error
    ) : CheckoutError(messageResId)

    data class AddressNotSelected(
        @StringRes override val messageResId: Int = R.string.error_address_not_selected
    ) : CheckoutError(messageResId)

    data class ShippingMethodNotSelected(
        @StringRes override val messageResId: Int = R.string.error_shipping_method_not_selected
    ) : CheckoutError(messageResId)

    data class PaymentMethodNotSelected(
        @StringRes override val messageResId: Int = R.string.error_payment_method_not_selected
    ) : CheckoutError(messageResId)

    data class GeneralLoadingError(
        @StringRes override val messageResId: Int = R.string.error_general_checkout
    ) : CheckoutError(messageResId)
}



