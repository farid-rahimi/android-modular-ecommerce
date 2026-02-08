package com.solutionium.domain.user

import com.solutionium.data.model.Result
import javax.inject.Inject

interface CheckOtpUseCase {
    suspend operator fun invoke(phoneNumber: String, otp: String): Result<Boolean, Unit>
}

internal class CheckOtpUseCaseImpl @Inject constructor(

) : CheckOtpUseCase {
    override suspend fun invoke(phoneNumber: String, otp: String): Result<Boolean, Unit> {
        // Implement the logic to check OTP
        return if (otp == "1234") Result.Success(true) else Result.Success(false)
    }
}