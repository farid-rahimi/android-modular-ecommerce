package com.solutionium.domain.user

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.UserWallet
import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetUserWalletUseCase {

    suspend operator fun invoke() : Flow<Result<UserWallet, GeneralError>>

}

internal class GetUserWalletUseCaseImpl @Inject constructor(

    private val userRepository: WooUserRepository

) : GetUserWalletUseCase {
    override suspend fun invoke(): Flow<Result<UserWallet, GeneralError>> =
        flow {
            emit(userRepository.getUserWallet())
        }

}