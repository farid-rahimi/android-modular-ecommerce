package com.solutionium.domain.user

import com.solutionium.data.model.ActionType
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.woo.user.WooUserRepository
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