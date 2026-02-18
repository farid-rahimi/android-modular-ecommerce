package com.solutionium.domain.user


import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.UserDetails
import com.solutionium.data.woo.user.WooUserRepository
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