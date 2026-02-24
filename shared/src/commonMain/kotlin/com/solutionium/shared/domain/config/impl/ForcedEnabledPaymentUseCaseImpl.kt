package com.solutionium.shared.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.domain.config.ForcedEnabledPaymentUseCase

class ForcedEnabledPaymentUseCaseImpl(
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