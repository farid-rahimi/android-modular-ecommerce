package com.solutionium.shared.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.ContactInfo
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.domain.config.GetContactInfoUseCase

class GetContactInfoUseCaseImpl(
    private val configRepository: AppConfigRepository
) : GetContactInfoUseCase {
    override suspend fun invoke(): ContactInfo? =
        when (val result = configRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.contact
            }

            is Result.Failure -> {
                null
            }
        }
}