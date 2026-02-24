package com.solutionium.shared.data.checkout

import com.solutionium.shared.data.model.Coupon
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result

interface CouponRepository {

    suspend fun getCouponByCode(couponCode: String): Result<Coupon?, GeneralError>

}