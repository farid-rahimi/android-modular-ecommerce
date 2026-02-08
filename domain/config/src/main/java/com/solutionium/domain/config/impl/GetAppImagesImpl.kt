package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.Result
import com.solutionium.domain.config.GetAppImages
import javax.inject.Inject

internal class GetAppImagesImpl @Inject constructor(
    private val appConfigRepository: AppConfigRepository
) : GetAppImages {
    override suspend fun invoke(): Map<Int, String> =
        when(val result = appConfigRepository.getAppConfig()) {
            is Result.Success -> result.data.images
            is Result.Failure -> emptyMap()
        }
}