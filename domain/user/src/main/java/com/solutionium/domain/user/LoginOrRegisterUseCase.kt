package com.solutionium.domain.user

import com.solutionium.shared.data.model.ActionType
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface LoginOrRegisterUseCase {
    suspend operator fun invoke(name: String, otp: String): Flow<Result<ActionType, GeneralError>>
}

class LoginOrRegisterUseCaseImpl(
    private val userRepository: WooUserRepository
) : LoginOrRegisterUseCase {
    override suspend fun invoke(name: String, otp: String): Flow<Result<ActionType, GeneralError>> = flow {
        emit(userRepository.loginOrRegister(name, otp))
    }

}