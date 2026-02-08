package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.Result
import com.solutionium.domain.config.ForcedEnabledPaymentUseCase
import javax.inject.Inject

class ForcedEnabledPaymentUseCaseImpl @Inject constructor(
    private val appConfigRepository: AppConfigRepository
) : ForcedEnabledPaymentUseCase {
    override suspend fun invoke(): List<String> =
        when (val result = appConfigRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.paymentForceEnabled
            }

            is Result.Failure -> {
                emptyList()
            }
        }
}