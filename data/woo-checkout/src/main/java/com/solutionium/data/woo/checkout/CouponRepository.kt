package com.solutionium.data.woo.checkout

import com.solutionium.data.model.Coupon
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result

interface CouponRepository {

    suspend fun getCouponByCode(couponCode: String): Result<Coupon?, GeneralError>

}