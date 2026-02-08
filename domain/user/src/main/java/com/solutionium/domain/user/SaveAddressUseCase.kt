package com.solutionium.domain.user

import com.solutionium.data.model.Address
import com.solutionium.data.woo.user.WooUserRepository
import javax.inject.Inject

interface SaveAddressUseCase {

    suspend operator fun invoke(address: Address)

}

internal class SaveAddressUseCaseImpl @Inject constructor(

    private val userRepository: WooUserRepository

): SaveAddressUseCase {
    override suspend fun invoke(address: Address) {
        userRepository.saveAddress(address)
    }
}