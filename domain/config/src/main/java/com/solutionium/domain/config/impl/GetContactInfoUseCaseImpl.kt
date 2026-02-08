package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.ContactInfo
import com.solutionium.data.model.Result
import com.solutionium.domain.config.GetContactInfoUseCase
import javax.inject.Inject

class GetContactInfoUseCaseImpl @Inject constructor(
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