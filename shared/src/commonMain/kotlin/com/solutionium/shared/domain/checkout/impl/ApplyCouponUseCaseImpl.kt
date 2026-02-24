package com.solutionium.shared.domain.checkout.impl

import com.solutionium.shared.data.model.CartItem
import com.solutionium.shared.data.model.Coupon
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.checkout.CouponRepository
import com.solutionium.shared.domain.checkout.ApplyCouponUseCase
import com.solutionium.shared.domain.checkout.CouponError
import com.solutionium.shared.domain.checkout.CouponErrorType
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ApplyCouponUseCaseImpl(
    private val couponRepository: CouponRepository // Inject the repository to fetch coupon data
) : ApplyCouponUseCase {
    override suspend fun invoke(
        couponCode: String,
        appliedCoupons: List<Coupon>,
        cartItems: List<CartItem>,
        cartSubtotal: Double
    ): Result<Coupon, CouponError> {

        // 1. Check for duplicates locally first to avoid an unnecessary API call.
        if (appliedCoupons.any { it.code.equals(couponCode, ignoreCase = true) }) {
            return Result.Failure(CouponError(CouponErrorType.AlreadyApplied, couponCode))
        }

        // 2. Fetch coupon details from the API.
        val coupon = when (val couponResult = couponRepository.getCouponByCode(couponCode)) {
            is Result.Success -> couponResult.data
            is Result.Failure -> return Result.Failure(
                CouponError(
                    CouponErrorType.NotExist,
                    couponCode
                )
            ) // Pass the API error back (e.g., "Coupon not found").
        }

        // 3. Perform eligibility checks using the fetched coupon data.
        if (coupon == null) {
            return Result.Failure(CouponError(CouponErrorType.NotExist, couponCode))
        }

        val validationError = validateCoupon(coupon, appliedCoupons, cartItems, cartSubtotal)
        if (validationError != null) {
            return Result.Failure(validationError)
        }

        // 4. If all checks pass, create the CouponLine object to be added to the state.
        val newCouponLine = coupon.copy()

        return Result.Success(newCouponLine)
    }

    /**
     * Performs a series of local validation checks based on WooCommerce rules.
     * Returns an error message string if validation fails, otherwise null.
     */
    private fun validateCoupon(
        coupon: Coupon,
        appliedCoupons: List<Coupon>,
        cartItems: List<CartItem>,
        cartSubtotal: Double
    ): CouponError? {

        if (!coupon.dateExpires.isNullOrEmpty()) {
            try {
                val expiryDate = LocalDateTime.parse(coupon.dateExpires)
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

                if (now > expiryDate) {
                    return CouponError(CouponErrorType.Expired)
                    //return "This coupon has expired."
                }
            } catch (_: IllegalArgumentException) {
                // e.g., return "Could not verify coupon's expiration date."
            }
        }

        // Minimum spend check
        if (coupon.minimumAmount > 0 && cartSubtotal < coupon.minimumAmount) {
            return CouponError(CouponErrorType.MinSpend, coupon.minimumAmount.toString())
        }

        // Maximum spend check
        if (coupon.maximumAmount > 0 && cartSubtotal > coupon.maximumAmount) {
            return CouponError(CouponErrorType.MaxSpend, coupon.maximumAmount.toString())
        }

        // Individual use check
        if (coupon.individualUse && appliedCoupons.isNotEmpty()) {
            return CouponError(CouponErrorType.IndividualUse)
        }
        // Also check if any already applied coupon is for individual use
        if (appliedCoupons.any { it.individualUse }) { // Assuming CouponLine has this flag
            return CouponError(CouponErrorType.IndividualAlready)
        }

        // Usage limit check (if the API provides a count)
        coupon.usageLimit?.let {
            if (coupon.usageCount >= it) {
                return CouponError(CouponErrorType.UsageLimit)
            }
        }


        // Product inclusion/exclusion checks
        val cartProductIds = cartItems.map { it.productId }.toSet()
        if (coupon.productIDS.isNotEmpty() &&
            cartProductIds.intersect(coupon.productIDS.toSet()).isEmpty()
        ) {
            return CouponError(CouponErrorType.Include)
        }
        if (coupon.excludedProductIDS.isNotEmpty() && cartProductIds.intersect(coupon.excludedProductIDS.toSet())
                .isNotEmpty()
        ) {
            return CouponError(CouponErrorType.Exclude)
        }

        // Category inclusion/exclusion checks
        val cartCategoryIds = cartItems.flatMap { it.categoryIDs }.toSet()

        // Included categories check
        if (coupon.productCategories.isNotEmpty() &&
            cartCategoryIds.intersect(coupon.productCategories.toSet()).isEmpty()
        ) {
            return CouponError(CouponErrorType.Include)
        }

        // Excluded categories check
        if (coupon.excludedProductCategories.isNotEmpty() &&
            cartCategoryIds.intersect(coupon.excludedProductCategories.toSet()).isNotEmpty()
        ) {
            return CouponError(CouponErrorType.Exclude)
        }

        val cartBrandIds = cartItems.flatMap { it.brandIDs }.toSet()

        // Included Brands Check
        if (coupon.brandIDS.isNotEmpty() &&
            cartBrandIds.intersect(coupon.brandIDS.toSet()).isEmpty()
        ) {
            return CouponError(CouponErrorType.Include)
        }

        // Excluded Brands Check
        if (coupon.excludedBrandIDS.isNotEmpty() &&
            cartBrandIds.intersect(coupon.excludedBrandIDS.toSet()).isNotEmpty()
        ) {
            return CouponError(CouponErrorType.Exclude)
        }

        // Exclude sale items check
        val hasSaleItems = cartItems.any { (it.regularPrice != null && it.regularPrice != it.currentPrice) || (it.appOffer > 0) }
        if (coupon.excludeSaleItems && hasSaleItems) {
            return CouponError(CouponErrorType.OnSalesLimit)
        }

        // All checks passed
        return null
    }
}

