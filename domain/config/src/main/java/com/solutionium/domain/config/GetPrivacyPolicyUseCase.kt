package com.solutionium.domain.config


interface GetPrivacyPolicyUseCase {

    suspend operator fun invoke(): String
}