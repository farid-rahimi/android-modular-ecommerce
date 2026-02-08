package com.solutionium.domain.checkout

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.PaymentGateway
import com.solutionium.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetPaymentGatewaysUseCase {

    suspend operator fun invoke(forcedEnabled: List<String>): Flow<Result<List<PaymentGateway>, GeneralError>>

}