package com.solutionium.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.Result
import com.solutionium.domain.config.PaymentMethodDiscountUseCase

class PaymentMethodDiscountUseCaseImpl(
    private val appConfigRepository: AppConfigRepository
) : PaymentMethodDiscountUseCase {
    override suspend fun invoke(): Map<String, Double> =
        when (val result = appConfigRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.paymentDiscount
            }

            is Result.Failure -> {
                emptyMap()
            }
        }

}