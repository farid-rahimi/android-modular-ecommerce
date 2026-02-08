package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.Result
import com.solutionium.domain.config.PaymentMethodDiscountUseCase
import javax.inject.Inject

class PaymentMethodDiscountUseCaseImpl @Inject constructor(
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