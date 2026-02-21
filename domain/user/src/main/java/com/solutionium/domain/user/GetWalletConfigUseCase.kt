package com.solutionium.domain.user

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.WalletConfig
import com.solutionium.data.woo.user.WooUserRepository

interface GetWalletConfigUseCase {

    suspend operator fun invoke(): Result<WalletConfig, GeneralError>
}

internal class GetWalletConfigUseCaseImpl(

    private val userRepository: WooUserRepository

) : GetWalletConfigUseCase {
    override suspend fun invoke(): Result<WalletConfig, GeneralError> =
        userRepository.getWalletConfig()

}