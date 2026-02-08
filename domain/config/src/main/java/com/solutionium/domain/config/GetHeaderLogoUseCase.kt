package com.solutionium.domain.config

interface GetHeaderLogoUseCase {

    suspend operator fun invoke(): String?

}