package com.solutionium.shared.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.domain.config.GetHeaderLogoUseCase

class GetHeaderLogoUseCaseImpl(
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