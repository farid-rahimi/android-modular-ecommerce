package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.BACSDetails
import com.solutionium.data.model.Result
import com.solutionium.domain.config.GetBACSDetailsUseCase

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