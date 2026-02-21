package com.solutionium.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.Result
import com.solutionium.domain.config.GetPrivacyPolicyUseCase

class GetPrivacyPolicyUseCaseImpl(
    private val appConfigRepository: AppConfigRepository
) : GetPrivacyPolicyUseCase {
    override suspend fun invoke(): String =
        when (val result = appConfigRepository.getPrivacyPolicy()) {
            is Result.Success -> {
                result.data
            }

            is Result.Failure -> {
                ""
            }
        }
}