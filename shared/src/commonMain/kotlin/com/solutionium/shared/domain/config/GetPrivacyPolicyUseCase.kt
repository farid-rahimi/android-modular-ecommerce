package com.solutionium.shared.domain.config


interface GetPrivacyPolicyUseCase {

    suspend operator fun invoke(): String
}