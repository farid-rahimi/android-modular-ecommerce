package com.solutionium.shared.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.BACSDetails
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.domain.config.GetBACSDetailsUseCase

class GetBACSDetailsUseCaseImpl(
    private val appConfigRepository: AppConfigRepository
) : GetBACSDetailsUseCase {
    override suspend fun invoke(): BACSDetails? =
        when (val result = appConfigRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.bacsDetails
            }

            is Result.Failure -> {
                null
            }
        }
}