package com.solutionium.domain.config.impl

import com.solutionium.data.config.AppConfigRepository
import com.solutionium.data.model.BannerItem
import com.solutionium.data.model.Result
import com.solutionium.domain.config.HomeBannersUseCase

internal class HomeBannersUseCaseImpl(
    private val configRepository: AppConfigRepository
) : HomeBannersUseCase {
    override suspend fun invoke(): List<BannerItem> =
        when (val result = configRepository.getAppConfig()) {
            is Result.Success -> {
                result.data.homeBanners
            }

            is Result.Failure -> {
                emptyList()
            }
        }
}