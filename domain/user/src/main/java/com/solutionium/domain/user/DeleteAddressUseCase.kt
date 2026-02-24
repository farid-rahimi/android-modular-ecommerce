package com.solutionium.domain.user

import com.solutionium.shared.data.model.Address
import com.solutionium.shared.data.user.WooUserRepository

interface DeleteAddressUseCase {

    suspend operator fun invoke(address: Address)

}

internal class DeleteAddressUseCaseImpl(

    private val userRepository: WooUserRepository

): DeleteAddressUseCase {
    override suspend fun invoke(address: Address) {
        userRepository.deleteAddress(address)
    }
}