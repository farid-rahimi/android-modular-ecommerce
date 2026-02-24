package com.solutionium.domain.user

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.UserWallet
import com.solutionium.shared.data.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetUserWalletUseCase {

    suspend operator fun invoke() : Flow<Result<UserWallet, GeneralError>>

}

internal class GetUserWalletUseCaseImpl(

    private val userRepository: WooUserRepository

) : GetUserWalletUseCase {
    override suspend fun invoke(): Flow<Result<UserWallet, GeneralError>> =
        flow {
            emit(userRepository.getUserWallet())
        }

}