package com.solutionium.domain.config

import com.solutionium.data.model.BannerItem

interface HomeBannersUseCase {

    suspend operator fun invoke(): List<BannerItem>

}