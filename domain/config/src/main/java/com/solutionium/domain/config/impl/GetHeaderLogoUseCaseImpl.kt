package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.Result
import com.solutionium.domain.config.GetHeaderLogoUseCase
import jakarta.inject.Inject

class GetHeaderLogoUseCaseImpl @Inject constructor(
    private val configRepository: AppConfigRepository
) : GetHeaderLogoUseCase {
    override suspend fun invoke(): String? =
        when (val result = configRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.headerLogoUrl
            }

            is Result.Failure -> {
                null
            }
        }
}