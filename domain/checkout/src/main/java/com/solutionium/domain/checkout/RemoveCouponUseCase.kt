package com.solutionium.domain.checkout

interface RemoveCouponUseCase {

    suspend operator fun invoke(couponCode: String)
}