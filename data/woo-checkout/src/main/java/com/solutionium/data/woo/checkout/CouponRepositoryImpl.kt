package com.solutionium.data.woo.checkout

import com.solutionium.data.api.woo.WooCouponRemoteSource
import com.solutionium.data.model.Coupon
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result

class CouponRepositoryImpl(
    private val remoteSource: WooCouponRemoteSource
) : CouponRepository {
    override suspend fun getCouponByCode(couponCode: String): Result<Coupon?, GeneralError> =
        remoteSource.getCouponByCode(couponCode)
}