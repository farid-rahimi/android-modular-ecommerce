package com.solutionium.shared.domain.user


import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.UserDetails
import com.solutionium.shared.data.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetCurrentUserUseCase {
    suspend operator fun invoke(): Flow<Result<UserDetails, GeneralError>>
}

internal class GetCurrentUserUseCaseImpl(
    private val wooUserRepository: WooUserRepository
) : GetCurrentUserUseCase {
    override suspend fun invoke(): Flow<Result<UserDetails, GeneralError>> = flow {

        emit(wooUserRepository.getMe())

    }
}