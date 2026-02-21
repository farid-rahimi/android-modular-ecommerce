package com.solutionium.domain.checkout

import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.Coupon
import com.solutionium.shared.data.model.Result

interface ApplyCouponUseCase {

    suspend operator fun invoke(
        couponCode: String,
        appliedCoupons: List<Coupon>,
        cartItems: List<CartItem>,
        cartSubtotal: Double
    ): Result<Coupon, CouponError>

}

data class CouponError(
    val errorType: CouponErrorType,
    val arg: String? = null
)

enum class CouponErrorType {
    AlreadyApplied,
    NotExist,
    Expired,
    MinSpend,
    MaxSpend,
    IndividualUse,
    IndividualAlready,
    UsageLimit,
    Include,
    Exclude,
    OnSalesLimit
}