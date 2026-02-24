package com.solutionium.domain.user

import com.solutionium.shared.data.model.ActionType
import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface LoginByUserPassUseCase {
    suspend operator fun invoke(user: String, pass: String): Flow<Result<ActionType, GeneralError>>
}

internal class LoginByUserPassUseCaseImpl(
    private val userRepository: WooUserRepository
) : LoginByUserPassUseCase {
    override suspend fun invoke(user: String, pass: String): Flow<Result<ActionType, GeneralError>> = flow {
        emit(userRepository.loginUserPass(user, pass))
    }

}