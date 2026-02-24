package com.solutionium.shared.domain.config

interface PaymentMethodDiscountUseCase {
    suspend operator fun invoke(): Map<String, Double>
}