package com.solutionium.shared.domain.user

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SendOtpUseCase {
    suspend operator fun invoke(phoneNumber: String): Flow<Result<Unit, GeneralError>>
}

internal class SendOtpUseCaseImpl(
    private val wooUserRepository: WooUserRepository
): SendOtpUseCase {
    override suspend fun invoke(phoneNumber: String): Flow<Result<Unit, GeneralError>> = flow {
        // Implement the logic to send OTP
        emit(wooUserRepository.sendOtp(phoneNumber))
    }
}