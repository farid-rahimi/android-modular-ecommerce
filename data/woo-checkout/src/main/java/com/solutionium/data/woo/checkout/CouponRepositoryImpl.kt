package com.solutionium.data.woo.checkout

import com.solutionium.shared.data.api.woo.WooCouponRemoteSource
import com.solutionium.shared.data.model.Coupon
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result

class CouponRepositoryImpl(
    private val remoteSource: WooCouponRemoteSource
) : CouponRepository {
    override suspend fun getCouponByCode(couponCode: String): Result<Coupon?, GeneralError> =
        remoteSource.getCouponByCode(couponCode)
}