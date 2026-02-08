package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.Result
import com.solutionium.domain.config.GetPrivacyPolicyUseCase
import javax.inject.Inject

class GetPrivacyPolicyUseCaseImpl @Inject constructor(
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