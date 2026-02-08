package com.solutionium.data.api.woo

import com.solutionium.data.model.Coupon
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result

interface WooCouponRemoteSource {

    suspend fun getCouponByCode(couponCode: String): Result<Coupon?, GeneralError>

}