package com.solutionium.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.AppVersion
import com.solutionium.shared.data.model.Result
import com.solutionium.domain.config.GetVersionsUseCase

class GetVersionsUseCaseImpl(
    private val configRepository: AppConfigRepository
) : GetVersionsUseCase {

    override suspend fun invoke(): AppVersion? =
        when (val result = configRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.appVersion
            }

            is Result.Failure -> {
                null
            }
        }

//    override suspend fun invoke(): AppVersion? {
//        return AppVersion(
//            latestVersion = "1.6",
//            minRequiredVersion = "1.5"
//        )
//    }
}