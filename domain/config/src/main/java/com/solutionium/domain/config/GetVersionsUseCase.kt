package com.solutionium.domain.config

import com.solutionium.data.model.AppVersion

interface GetVersionsUseCase {

    suspend operator fun invoke(): AppVersion?

}