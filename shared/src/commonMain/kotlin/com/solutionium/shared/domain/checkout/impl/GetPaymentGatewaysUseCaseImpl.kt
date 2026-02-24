package com.solutionium.shared.domain.checkout.impl

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.PaymentGateway
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.checkout.CheckoutRepository
import com.solutionium.shared.domain.checkout.GetPaymentGatewaysUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPaymentGatewaysUseCaseImpl(
    private val checkoutRepository: CheckoutRepository,
) : GetPaymentGatewaysUseCase {
    override suspend fun invoke(forcedEnabled: List<String>): Flow<Result<List<PaymentGateway>, GeneralError>> = flow {
        emit(checkoutRepository.getPaymentGateways(forcedEnabled))
    }
}