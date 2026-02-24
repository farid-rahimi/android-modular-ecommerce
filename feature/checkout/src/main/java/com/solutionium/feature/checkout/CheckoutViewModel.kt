package com.solutionium.feature.checkout

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionium.shared.data.model.Address
import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.Coupon
import com.solutionium.shared.data.model.FeeLine
import com.solutionium.shared.data.model.Metadata
import com.solutionium.shared.data.model.NewOrderData
import com.solutionium.shared.data.model.PaymentGateway
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.ShippingMethod
import com.solutionium.shared.data.model.getPartialPaymentAmount
import com.solutionium.shared.data.model.getPaymentRedirectUrl
import com.solutionium.shared.data.model.getWalletPartialPaymentMeta
import com.solutionium.shared.domain.cart.ClearCartUseCase
import com.solutionium.shared.domain.cart.ObserveCartUseCase
import com.solutionium.shared.domain.checkout.ApplyCouponUseCase
import com.solutionium.shared.domain.checkout.CreateOrderUseCase
import com.solutionium.shared.domain.checkout.GetOrderStatusUseCase
import com.solutionium.shared.domain.checkout.GetPaymentGatewaysUseCase
import com.solutionium.shared.domain.checkout.GetShippingMethodsUseCase
import com.solutionium.domain.config.ForcedEnabledPaymentUseCase
import com.solutionium.domain.config.GetBACSDetailsUseCase
import com.solutionium.domain.config.PaymentMethodDiscountUseCase
import com.solutionium.domain.user.GetUserWalletUseCase
import com.solutionium.domain.user.LoadAddressesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object FeeKeys {
    const val PAYMENT_DISCOUNT = "payment_discount"
}

class CheckoutViewModel(
    private val observeCartUseCase: ObserveCartUseCase,
    private val getShippingMethodsUseCase: GetShippingMethodsUseCase,
    private val getForcedEnabledPayment: ForcedEnabledPaymentUseCase,
    private val getPaymentGatewaysUseCase: GetPaymentGatewaysUseCase,
    private val loadAddressesUseCase: LoadAddressesUseCase,
    private val applyCouponUseCase: ApplyCouponUseCase, // <-- New Use Case
    private val createOrderUseCase: CreateOrderUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val getOrderStatusUseCase: GetOrderStatusUseCase, // ADD THIS DEPENDENCY
    private val paymentMethodDiscountUseCase: PaymentMethodDiscountUseCase,
    private val getBACSDetails: GetBACSDetailsUseCase,
    private val getUserWalletUseCase: GetUserWalletUseCase,
    private val context: Context
) : ViewModel() {

    private val _state: MutableStateFlow<CheckoutUiState> =
        MutableStateFlow(CheckoutUiState())
    val state = _state.asStateFlow()

    private var verificationJob: Job? = null

    init {
        initCheckout()

    }


    private fun initCheckout() {
        observeCart()
        observeAddress()
        loadShippingMethods()
        loadPaymentGateways()
        loadPaymentDiscount()
        loadUserWallet()
    }

    private fun loadUserWallet() {

        viewModelScope.launch {
            _state.update { it.copy(loadingWallet = true) }
            getUserWalletUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(userWallet = result.data, loadingWallet = false) }
                    }

                    is Result.Failure -> _state.update { it.copy(loadingWallet = false) }

                }


            }
        }

    }


    private fun observeCart() {

        viewModelScope.launch {
            observeCartUseCase().collect { items ->
                val subTotalPrice = items.sumOf { it.currentPrice * it.quantity }
                val itemsNeedAttention = items.any { it.requiresAttention }
                _state.update { it.copy(cartItems = items, subTotal = subTotalPrice) }
                recalculateTotals()
            }
        }

    }

    private fun observeAddress() {
        viewModelScope.launch {
            loadAddressesUseCase().collect { addresses ->
                val defaultAddress = addresses.find { address -> address.isDefault }
//                if (defaultAddress == null && addresses.isNotEmpty()) {
//                    defaultAddress = addresses.first()
//                    setDefaultAddressUseCase(defaultAddress.id ?: -1 )
//                }
                _state.update {
                    it.copy(
                        shippingAddress = defaultAddress,
                        availableAddresses = addresses
                    )
                }
            }
        }
    }


    fun loadDefaultAddress() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingAddress = true) }
            // Simulate loading an address or fetch from repository
            // val loadedAddress = addressRepository.getLastUsedAddress()
            // _state.update { it.copy(shippingAddress = loadedAddress, isLoadingAddress = false) }
        }
    }

    private fun loadPaymentGateways() =
        viewModelScope.launch {

            _state.update { it.copy(isLoadingPaymentGateways = true) }

            val forcedEnabledList = getForcedEnabledPayment()

            getPaymentGatewaysUseCase(forcedEnabledList).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(paymentGateways = result.data) }
                        // Auto-select the first gateway if none selected
                        if (_state.value.selectedPaymentGateway == null && result.data.isNotEmpty()) {
                            selectPaymentGateway(result.data.first())
                        }
                    }

                    is Result.Failure -> {
                        _state.update { it.copy(error = CheckoutError.GeneralLoadingError()) }
                    }
                }
            }.also {
                _state.update { it.copy(isLoadingPaymentGateways = false) }
            }
        }

    private fun loadPaymentDiscount() {
        viewModelScope.launch {
            val discounts = paymentMethodDiscountUseCase()
            _state.update { it.copy(paymentMethodDiscounts = discounts) }
            if (_state.value.paymentGateways.isNotEmpty() && discounts.isNotEmpty())
                selectPaymentGateway(_state.value.paymentGateways.first())
        }
    }

    private fun loadShippingMethods() =
        viewModelScope.launch {

            _state.update { it.copy(isLoadingShippingMethods = true) }

            getShippingMethodsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val freeShippingMethod = result.data.find { it.isFreeShippingByCoupon() }
                        val filteredMethods =
                            result.data.filter { !it.isFreeShippingByCoupon() && !it.isFreeShippingByMinOrder() }
                        val freeShippingByMinOrder =
                            result.data.find { it.isFreeShippingByMinOrder() }

                        _state.update { state ->
                            state.copy(
                                shippingMethods = filteredMethods,
                                freeShippingMethodByCoupon = freeShippingMethod,
                                freeShippingMethodByMinOrder = freeShippingByMinOrder
                            )
                        }
                        // Auto-select the first method if none selected
                        if (_state.value.selectedShippingMethod == null && filteredMethods.isNotEmpty()) {
                            selectShipping(filteredMethods.first())
                        }
                    }

                    is Result.Failure -> {
                        _state.update { it.copy(error = CheckoutError.GeneralLoadingError()) }
                    }
                }
            }.also {
                _state.update { it.copy(isLoadingShippingMethods = false) }
            }
        }

    fun selectShipping(method: ShippingMethod) {
        if (_state.value.selectedShippingMethod == method)
            return

        val shippingCost = method.calculateShippingCost(_state.value.subTotal)
        _state.update {
            it.copy(
                selectedShippingMethod = method,
                shippingCost = shippingCost,
            )
        }
        recalculateTotals()
    }

    fun selectPaymentGateway(gateway: PaymentGateway) {
        val isInstallment = paymentMethodIsInstallment(gateway.id)
        //val fees = _state.value.fees

        _state.update {
            it.copy(
                selectedPaymentGateway = gateway,
                //fees = fees,
                isInstallment = isInstallment
            )
        }
        recalculateTotals()
    }

    fun paymentDiscountAmount(methodId: String): Double {
        val discountPercent = paymentMethodHasDiscount(methodId)
        return (discountPercent / 100.0) * (_state.value.subTotal - _state.value.totalDiscount - _state.value.paidByWallet)
    }

    private fun paymentMethodHasDiscount(methodId: String): Double {
        return _state.value.paymentMethodDiscounts[methodId] ?: 0.0
    }

    private fun paymentMethodIsInstallment(methodId: String): Boolean {
        return listOf("WC_Gateway_SnappPay", "WC_Gateway_TorobPay").contains(methodId)
    }


    fun applyCoupon(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(isApplyingCoupon = true, couponError = null) }

            // Here, you would typically make a specific API call to validate the coupon
            // and get its details. This is better than waiting for the final order creation.
            // Let's assume you have an ApplyCouponUseCase for this.
            when (val result = applyCouponUseCase(
                code,
                state.value.appliedCoupons,
                state.value.cartItems,
                state.value.subTotal
            )) {
                is Result.Success -> {
                    // Success, update the list of applied coupons
                    _state.update {
                        it.copy(
                            isApplyingCoupon = false,
                            // Add new coupon and filter out duplicates
                            appliedCoupons = (it.appliedCoupons + result.data).distinctBy { c -> c.code }
                        )
                    }

                    if (result.data.freeShipping) {
                        _state.value.freeShippingMethodByCoupon?.let {
                            _state.update { state -> state.copy(freeShippingByCouponIsActive = true) }
                            selectShipping(it)
                        }

                    }
                    recalculateTotals() // Recalculate after applying a coupon
                    // IMPORTANT: Re-fetch shipping methods as a new coupon might have enabled free shipping
                    //getShippingMethodsForZone()
                }

                is Result.Failure -> {

                    _state.update {
                        it.copy(
                            isApplyingCoupon = false,
                            couponError = result.error
                        )
                    }

                }
            }
        }
    }

    fun removeCoupon(coupon: Coupon) {
        viewModelScope.launch {
            // No API call is strictly needed, just remove it from the local state.
            // The final list will be sent when the order is placed.
            val updatedCoupons = state.value.appliedCoupons.filter { it.id != coupon.id }
            _state.update { it.copy(appliedCoupons = updatedCoupons, couponError = null) }

            if (coupon.freeShipping && _state.value.freeShippingByCouponIsActive) {
                _state.update { it.copy(freeShippingByCouponIsActive = false) }
                selectShipping(_state.value.shippingMethods.first())
            }

            recalculateTotals() // Recalculate after removing a coupon
            // IMPORTANT: Re-fetch shipping methods as removing the coupon might disable free shipping
            //getShippingMethodsForZone()
        }
    }

    private fun recalculateTotals() {
        val currentState = _state.value
        val subtotal = currentState.cartItems.sumOf { it.currentPrice * it.quantity }
        var totalDiscount = 0.0
        val fees = _state.value.fees
        val discountAmount =
            paymentDiscountAmount(methodId = state.value.selectedPaymentGateway?.id ?: "none")
        if (discountAmount > 0.0) {
            fees[FeeKeys.PAYMENT_DISCOUNT] = -1 * discountAmount
        } else {
            fees.remove(FeeKeys.PAYMENT_DISCOUNT)
        }


        currentState.appliedCoupons.forEach { coupon ->
            when (coupon.discountType) {
                "percent" -> {
                    // Note: WooCommerce percent discount might be applied on subtotal or specific items.
                    // This is a simplified calculation.
                    val discountValue = coupon.amount
                    totalDiscount += (subtotal * (discountValue / 100.0))
                }

                "fixed_cart" -> {
                    val discountValue = coupon.amount
                    totalDiscount += discountValue
                }

                "fixed_product" -> {
                    // This logic can get complex, as it applies only to specific products.
                    // For now, we'll treat it like a fixed cart discount for simplicity.
                    val discountValue = coupon.amount
                    totalDiscount += discountValue
                }
            }
        }

        // Make sure discount doesn't exceed the subtotal
        totalDiscount = totalDiscount.coerceAtMost(subtotal)

        var totalFees = 0.0
        fees.values.forEach { fee ->
            totalFees += fee
        }

        // Check if a free shipping coupon has been applied
        val hasFreeShippingCoupon = currentState.appliedCoupons.any { it.freeShipping }
        val freeShippingByProductShippingClass =
            _state.value.cartItems.any { it.shippingClass == "free-shipping" } // Implement logic if needed
        var shippingFee =
            if (hasFreeShippingCoupon || freeShippingByProductShippingClass) 0.0 else (currentState.shippingCost)

        _state.value.freeShippingMethodByMinOrder?.let {
            if (it.isEligibleForMinOrderAmount(subTotal = subtotal)) {
                _state.update { state -> state.copy(freeShippingByMinOrderIsActive = true) }
                selectShipping(it)
                shippingFee = 0.0
            } else {
                _state.update { state -> state.copy(freeShippingByMinOrderIsActive = false) }
                //selectShipping(_state.value.shippingMethods.first())
            }
        }


        var finalTotal = (subtotal - totalDiscount + shippingFee + totalFees).coerceAtLeast(0.0)

        var walletPaymentAmount = 0.0
        if (_state.value.useWallet) {
            val balance = _state.value.userWallet?.balance ?: 0.0
            walletPaymentAmount = balance.coerceAtMost(finalTotal)
            finalTotal -= walletPaymentAmount
        }

        _state.update {
            it.copy(
                subTotal = subtotal,
                totalDiscount = totalDiscount,
                shippingCost = shippingFee,
                fees = fees,
                paidByWallet = walletPaymentAmount,
                totalFees = totalFees,
                total = finalTotal
            )
        }
    }


    fun confirmOrder() {

        viewModelScope.launch {
            val currentState = _state.value
            val cartItems: List<CartItem> = currentState.cartItems
            val shippingAddress = currentState.shippingAddress
            val paymentGateway = currentState.selectedPaymentGateway
            val shippingMethod = currentState.selectedShippingMethod
            val couponCodes = if (currentState.appliedCoupons.isNotEmpty()) {
                currentState.appliedCoupons.map { it.code }
            } else {
                null
            }

            val totalWalletPayment = (currentState.useWallet && currentState.total == 0.0)

            val feeLines = currentState.fees.map { (key, value) ->
                FeeLine(
                    name = key,
                    total = value
                )
            }.toMutableList()

            val metadata: MutableList<Metadata> = emptyList<Metadata>().toMutableList()
            if (currentState.useWallet) {

                feeLines.add(
                    FeeLine(
                        name = "via_wallet",
                        total = -1 * currentState.paidByWallet,
                        metadata = listOf(getWalletPartialPaymentMeta())
                    )
                )


                metadata.add(getPartialPaymentAmount(currentState.paidByWallet.toString()))

            }

            metadata.add(getPaymentRedirectUrl())


            if (cartItems.isEmpty()) {
                _state.update { it.copy(error = CheckoutError.EmptyCart()) }
                return@launch
            }
            if (shippingAddress == null) {
                _state.update { it.copy(error = CheckoutError.AddressNotSelected()) }
                return@launch
            }
            if (shippingMethod == null) {
                _state.update { it.copy(error = CheckoutError.ShippingMethodNotSelected()) }
                return@launch
            }
            if (paymentGateway == null) {
                _state.update { it.copy(error = CheckoutError.PaymentMethodNotSelected()) }
                return@launch
            }


            var status = if (paymentGateway.id == "bacs" && !totalWalletPayment) "on-hold" else null
            if (totalWalletPayment) {
                status = "processing"
            }

            val orderData = NewOrderData(
                coupon = couponCodes,
                cartItems = cartItems,
                shipping = shippingAddress,
                paymentMethod = if (totalWalletPayment) "wallet" else paymentGateway.id,
                paymentMethodTitle = if (totalWalletPayment) "Wallet" else paymentGateway.title,
                shippingMethod = shippingMethod.copy(cost = currentState.shippingCost.toString()),
                billing = shippingAddress, // Assuming billing is same as shipping
                feeLines = feeLines.ifEmpty { null },
                metaData = metadata,
                //setPaid = totalWalletPayment,
                status = status

            )

            //_state.update { it.copy(isConfirmingOrder = true) }
            _state.update { it.copy(placeOrderStatus = PlaceOrderStatus.InProgress) }

            createOrderUseCase(
                orderData
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("confirmOrder", "success: ")
                        val orderResponse = result.data

                        if (totalWalletPayment) {
                            clearCartUseCase()
                            _state.update {
                                it.copy(
                                    placeOrderStatus = PlaceOrderStatus.Success(
                                        orderId = orderResponse.id,
                                        orderTotal = currentState.paidByWallet.toString(),
                                    ),
                                )
                            }
                        } else if (paymentGateway.id == "bacs") {
                            clearCartUseCase()
                            val bacsDetails = getBACSDetails()
                            _state.update {
                                it.copy(
                                    placeOrderStatus = PlaceOrderStatus.BACSSuccess(
                                        orderId = orderResponse.id,
                                        orderTotal = orderResponse.total,
                                        bacsDetails = bacsDetails
                                    ),
                                )
                            }
                        } else if (!orderResponse.paymentUrl.isNullOrBlank()) {
                            // We received a payment URL, so we transition to AwaitingPayment
                            _state.update {
                                it.copy(
                                    placeOrderStatus = PlaceOrderStatus.AwaitingPayment(
                                        paymentUrl = orderResponse.paymentUrl ?: "",
                                        orderId = orderResponse.id
                                    )
                                )
                            }
                        } else {
                            // No payment URL means payment was direct or an error occurred
                            // For now, let's treat it as an order creation success.

                        }
                    }

                    is Result.Failure -> {
                        Log.d("confirmOrder", "failed: ")
                        _state.update {
                            it.copy(
                                placeOrderStatus = PlaceOrderStatus.Failed(
                                    errorMessage = result.error.toString()
                                        ?: "An unknown error occurred."
                                ),
                            )
                        }
                        //_state.update { it.copy(error = result.error, isConfirmingOrder = false) }
                    }
                }
            }

        }

    }

    fun verifyOrderStatusAfterPayment() {
        // Prevent multiple simultaneous checks
        if (verificationJob?.isActive == true) return

        val currentState = _state.value.placeOrderStatus
        if (currentState !is PlaceOrderStatus.AwaitingPayment) return

        val orderId = currentState.orderId

        verificationJob = viewModelScope.launch {
            // Show a loading indicator on the "Awaiting" screen
            // You can add a new boolean to your state like `isVerifyingPayment` if needed.

            delay(1000) // Small delay to allow payment processors to update

            when (val result = getOrderStatusUseCase(orderId)) {
                is Result.Success -> {
                    val orderStatus = result.data.status
                    // Common WooCommerce statuses: "completed", "processing", "on-hold", "failed", "cancelled"
                    if (orderStatus == "completed" || orderStatus == "processing") {
                        clearCartUseCase()
                        _state.update {
                            it.copy(
                                placeOrderStatus = PlaceOrderStatus.Success(
                                    orderId = result.data.id,
                                    orderTotal = result.data.total
                                )
                            )
                        }
                    } else {
                        // Status is "failed", "cancelled", "on-hold", etc.
                        _state.update {
                            it.copy(
                                placeOrderStatus = PlaceOrderStatus.Failed(
                                    errorMessage = context.getString(
                                        R.string.payment_unsuccess,
                                        orderStatus
                                    ),
                                    canRetry = false // User should probably go to their account
                                )
                            )
                        }
                    }
                }

                is Result.Failure -> {
                    _state.update {
                        it.copy(
                            placeOrderStatus = PlaceOrderStatus.Failed(
                                errorMessage = result.error.toString(),
                                canRetry = false // The error was in checking, so they can retry checking
                            )
                        )
                    }
                }
            }
        }
    }

    fun resetOrderStatus() {
        _state.update { it.copy(placeOrderStatus = PlaceOrderStatus.Idle) }
    }


    fun onAddressSelected(address: Address) {
        _state.update {
            it.copy(
                shippingAddress = address,
                isAddressListExpanded = false // Collapse the list after selection
            )
        }
    }

    fun onToggleAddressList() {
        _state.update { it.copy(isAddressListExpanded = !it.isAddressListExpanded) }
    }

    fun onUseWalletChange(useWallet: Boolean) {

//        var walletPaymentAmount = 0.0
//        val fees = _state.value.fees
//        if (useWallet) {
//            val balance = _state.value.userWallet?.balance ?: 0.0
//            walletPaymentAmount = Math.min(balance, _state.value.total)
//            if (walletPaymentAmount > 0.0) {
//                fees["Wallet Pay"] = -1 * walletPaymentAmount
//            }
//        } else {
//            fees.remove("Wallet Pay")
//        }

        _state.update {
            it.copy(
                //fees = fees,
                useWallet = useWallet,
                //paidByWallet = walletPaymentAmount
            )
        }

        recalculateTotals()
        recalculateTotals()

//        _state.update { currentState ->
//            val remainingTotal = if (useWallet) {
//                (currentState.total - currentState.walletBalance).coerceAtLeast(0.0)
//            } else {
//                currentState.total
//            }
//            currentState.copy(
//                useWallet = useWallet,
//                remainingTotalAfterWallet = remainingTotal
//            )
//        }
    }

}

