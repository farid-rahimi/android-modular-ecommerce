package com.solutionium.domain.config

import com.solutionium.shared.data.model.AppVersion

interface GetVersionsUseCase {

    suspend operator fun invoke(): AppVersion?

}