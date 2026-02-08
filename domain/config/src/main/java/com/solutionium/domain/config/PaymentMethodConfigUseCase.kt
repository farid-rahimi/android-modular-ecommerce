package com.solutionium.domain.config

interface PaymentMethodDiscountUseCase {
    suspend operator fun invoke(): Map<String, Double>
}