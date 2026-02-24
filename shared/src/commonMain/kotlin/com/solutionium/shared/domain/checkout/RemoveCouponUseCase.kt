package com.solutionium.shared.domain.checkout

interface RemoveCouponUseCase {

    suspend operator fun invoke(couponCode: String)
}