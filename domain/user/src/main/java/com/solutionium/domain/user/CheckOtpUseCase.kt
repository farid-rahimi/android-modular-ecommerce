package com.solutionium.domain.user

import com.solutionium.shared.data.model.Result

interface CheckOtpUseCase {
    suspend operator fun invoke(phoneNumber: String, otp: String): Result<Boolean, Unit>
}

internal class CheckOtpUseCaseImpl(

) : CheckOtpUseCase {
    override suspend fun invoke(phoneNumber: String, otp: String): Result<Boolean, Unit> {
        // Implement the logic to check OTP
        return if (otp == "1234") Result.Success(true) else Result.Success(false)
    }
}