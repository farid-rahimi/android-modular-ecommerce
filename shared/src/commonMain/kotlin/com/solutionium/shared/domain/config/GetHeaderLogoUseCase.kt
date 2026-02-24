package com.solutionium.shared.domain.config

interface GetHeaderLogoUseCase {

    suspend operator fun invoke(): String?

}