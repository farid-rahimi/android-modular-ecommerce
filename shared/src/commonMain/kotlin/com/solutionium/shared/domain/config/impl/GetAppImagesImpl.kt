package com.solutionium.shared.domain.config.impl

import com.solutionium.shared.data.config.AppConfigRepository
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.domain.config.GetAppImages

internal class GetAppImagesImpl(
    private val appConfigRepository: AppConfigRepository
) : GetAppImages {
    override suspend fun invoke(): Map<Int, String> =
        when(val result = appConfigRepository.getAppConfig()) {
            is Result.Success -> result.data.images
            is Result.Failure -> emptyMap()
        }
}