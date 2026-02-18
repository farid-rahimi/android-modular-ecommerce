package com.solutionium.data.api.woo.impl

import com.solutionium.data.api.woo.WooCouponRemoteSource
import com.solutionium.data.api.woo.converters.toModel
import com.solutionium.data.api.woo.handleNetworkResponse
import com.solutionium.data.model.Coupon
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.network.clients.WooCheckoutOrderClient

class WooCouponRemoteSourceImpl(
    private val checkoutService: WooCheckoutOrderClient
): WooCouponRemoteSource {
    override suspend fun getCouponByCode(couponCode: String): Result<Coupon?, GeneralError> =

        handleNetworkResponse(
            networkCall = { checkoutService.getCoupons(couponCode) },
            mapper = { it.firstOrNull()?.toModel() }
        )

}