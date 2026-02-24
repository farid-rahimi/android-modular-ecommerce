package com.solutionium.shared.domain.checkout

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.PaymentGateway
import com.solutionium.shared.data.model.Result
import kotlinx.coroutines.flow.Flow

interface GetPaymentGatewaysUseCase {

    suspend operator fun invoke(forcedEnabled: List<String>): Flow<Result<List<PaymentGateway>, GeneralError>>

}