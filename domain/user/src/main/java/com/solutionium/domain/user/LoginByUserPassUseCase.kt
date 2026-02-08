package com.solutionium.domain.user

import com.solutionium.data.model.ActionType
import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LoginByUserPassUseCase {
    suspend operator fun invoke(user: String, pass: String): Flow<Result<ActionType, GeneralError>>
}

internal class LoginByUserPassUseCaseImpl @Inject constructor(
    private val userRepository: WooUserRepository
) : LoginByUserPassUseCase {
    override suspend fun invoke(user: String, pass: String): Flow<Result<ActionType, GeneralError>> = flow {
        emit(userRepository.loginUserPass(user, pass))
    }

}