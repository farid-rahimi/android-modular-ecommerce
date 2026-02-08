package com.solutionium.domain.user

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.WalletConfig
import com.solutionium.data.woo.user.WooUserRepository
import javax.inject.Inject

interface GetWalletConfigUseCase {

    suspend operator fun invoke(): Result<WalletConfig, GeneralError>
}

internal class GetWalletConfigUseCaseImpl @Inject constructor(

    private val userRepository: WooUserRepository

) : GetWalletConfigUseCase {
    override suspend fun invoke(): Result<WalletConfig, GeneralError> =
        userRepository.getWalletConfig()

}