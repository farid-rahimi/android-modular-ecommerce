package com.solutionium.domain.checkout.impl

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.PaymentGateway
import com.solutionium.data.model.Result
import com.solutionium.data.woo.checkout.CheckoutRepository
import com.solutionium.domain.checkout.GetPaymentGatewaysUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPaymentGatewaysUseCaseImpl @Inject constructor(
    private val checkoutRepository: CheckoutRepository,
) : GetPaymentGatewaysUseCase {
    override suspend fun invoke(forcedEnabled: List<String>): Flow<Result<List<PaymentGateway>, GeneralError>> = flow {
        emit(checkoutRepository.getPaymentGateways(forcedEnabled))
    }
}