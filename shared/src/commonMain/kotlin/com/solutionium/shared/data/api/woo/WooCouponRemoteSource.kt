package com.solutionium.shared.data.api.woo

import com.solutionium.shared.data.model.Coupon
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result


interface WooCouponRemoteSource {

    suspend fun getCouponByCode(couponCode: String): Result<Coupon?, GeneralError>

}