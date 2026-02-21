package com.solutionium.domain.user

import com.solutionium.shared.data.model.Address
import com.solutionium.data.woo.user.WooUserRepository

interface SaveAddressUseCase {

    suspend operator fun invoke(address: Address)

}

internal class SaveAddressUseCaseImpl(

    private val userRepository: WooUserRepository

): SaveAddressUseCase {
    override suspend fun invoke(address: Address) {
        userRepository.saveAddress(address)
    }
}