package com.solutionium.shared.domain.config

interface ForcedEnabledPaymentUseCase {
    suspend operator fun invoke(): List<String>
}