package com.solutionium.shared.data.api.woo.impl

import com.solutionium.shared.data.api.woo.converters.toModel
import com.solutionium.shared.data.model.Coupon
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.api.woo.WooCouponRemoteSource
import com.solutionium.shared.data.api.woo.handleNetworkResponse
import com.solutionium.shared.data.network.clients.WooCheckoutOrderClient
import com.solutionium.shared.data.model.Result


class WooCouponRemoteSourceImpl(
    private val checkoutService: WooCheckoutOrderClient
): WooCouponRemoteSource {
    override suspend fun getCouponByCode(couponCode: String): Result<Coupon?, GeneralError> =

        handleNetworkResponse(
            networkCall = { checkoutService.getCoupons(couponCode) },
            mapper = { it.firstOrNull()?.toModel() }
        )

}