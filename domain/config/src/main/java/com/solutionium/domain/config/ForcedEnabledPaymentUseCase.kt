package com.solutionium.domain.config

interface ForcedEnabledPaymentUseCase {
    suspend operator fun invoke(): List<String>
}