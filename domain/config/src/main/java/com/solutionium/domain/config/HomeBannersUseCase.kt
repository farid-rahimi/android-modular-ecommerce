package com.solutionium.domain.config

import com.solutionium.shared.data.model.BannerItem

interface HomeBannersUseCase {

    suspend operator fun invoke(): List<BannerItem>

}